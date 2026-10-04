package io.github.riiimc.riiilib.transformer;

import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RiiiLibTransformer {
    static boolean initialized = false;
    public static List<String> exclusivePackages = new ArrayList<>();

    static {
        initialize();
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        exclusivePackages.add("io/github/kosianodangoo/trialmonolith/transformer");
        try {
            ILaunchPluginService plugin = new RiiiLibPlugin();

            Field field = Launcher.class.getDeclaredField("launchPlugins");
            field.setAccessible(true);
            LaunchPluginHandler pluginHandler = (LaunchPluginHandler) field.get(Launcher.INSTANCE);
            field = LaunchPluginHandler.class.getDeclaredField("plugins");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, ILaunchPluginService> map = (Map<String, ILaunchPluginService>) field.get(pluginHandler);
            map.put(plugin.name(), plugin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        initialized = true;
    }
    public static int transform(ILaunchPluginService.Phase phase, ClassNode classNode) {
        if (!initialized) return 0;
        return 1;
    }
}
