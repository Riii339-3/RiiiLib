package io.github.riiimc.riiilib.service;

import com.sun.tools.attach.VirtualMachine;
import sun.misc.Unsafe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public final class AgentLoader {

    private static Path agentPath;
    private static Unsafe UNSAFE;
    private static boolean SUCCESS = false;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);

            UNSAFE = (Unsafe) field.get(null);
            SUCCESS = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AgentLoader() {
    }

    public static void load() throws Exception {
        agentPath = extractAgent();

        if (agentPath == null) {
            return;
        }

        if (!allowAttachSelf()) {
            return;
        }

        loadAgent(agentPath);
    }

    public static void loadAgent(Path agentPath) throws Exception {
        String pid = ProcessHandle.current().pid() + "";
        VirtualMachine vm = VirtualMachine.attach(pid);

        try {
            vm.loadAgent(agentPath.toAbsolutePath().toString());
        } finally {
            vm.detach();
        }
    }

    public static boolean allowAttachSelf() {
        if (!SUCCESS || UNSAFE == null) {
            return false;
        }

        String className = "sun.tools.attach.HotSpotVirtualMachine";

        try {
            Class<?> clazz = Class.forName(className);

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == boolean.class
                        && field.getName()
                        .toLowerCase(Locale.getDefault())
                        .contains("allow_attach_self")) {

                    long offset = UNSAFE.staticFieldOffset(field);
                    Object base = UNSAFE.staticFieldBase(field);

                    UNSAFE.putBoolean(base, offset, true);
                    return true;
                }
            }
        } catch (ClassNotFoundException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static Path extractAgent() throws IOException {
        InputStream resource = AgentLoader.class.getResourceAsStream(
                "/META-INF/riiilib/agent.jar"
        );

        if (resource == null) {
            return null;
        }

        try (InputStream input = resource) {
            Path temp = Files.createTempFile(
                    "riiilib-agent-",
                    ".jar"
            );

            try {
                Files.copy(
                        input,
                        temp,
                        StandardCopyOption.REPLACE_EXISTING
                );

                temp.toFile().deleteOnExit();
                return temp;
            } catch (IOException e) {
                Files.deleteIfExists(temp);
                throw e;
            }
        }
    }
}