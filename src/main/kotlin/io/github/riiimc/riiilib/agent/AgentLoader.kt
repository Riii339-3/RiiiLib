package io.github.riiimc.riiilib.agent

import com.sun.tools.attach.VirtualMachine
import io.github.riiimc.riiilib.storage.StorageAPI
import sun.misc.Unsafe
import java.lang.reflect.Field
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.Locale.getDefault

@Deprecated("Use AgentLoader.load() instead")
object AgentLoader {
    lateinit var agentPath: Path
    private var UNSAFE: Unsafe
    private var SUCCESS: Boolean = false
    init {

        var temp: Unsafe? = null
        var s = false
        try {
            val f: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
            f.setAccessible(true)
            temp = f.get(null) as Unsafe?
            s = true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        UNSAFE = temp!!
        SUCCESS = s
    }
    @JvmStatic
    fun load() {
        agentPath = extractAgent(
            //"META-INF/riiilib/agent.jar",
        ) ?: return
        if (!allowAttachSelf()) return
        loadAgent(agentPath)
    }

    fun loadAgent(agentPath: Path) {
        val pid = ProcessHandle.current().pid().toString()
        val vm = VirtualMachine.attach(pid)

        try {
            vm.loadAgent(agentPath.toAbsolutePath().toString())
        } finally {
            vm.detach()
        }
    }

    fun allowAttachSelf(): Boolean {
        if (!SUCCESS) return false
        val className = "sun.tools.attach.HotSpotVirtualMachine"
        try {
            val clazz = Class.forName(className)
            for (f in clazz.getDeclaredFields()) {
                if (f.type === Boolean::class.javaPrimitiveType && f.name.lowercase(getDefault())
                        .contains("allow_attach_self")
                ) {
                    val offset: Long = UNSAFE.staticFieldOffset(f)
                    val base: Any? = UNSAFE.staticFieldBase(f)
                    UNSAFE.putBoolean(base, offset, true)
                    return true
                }
            }
        } catch (ignored: ClassNotFoundException) {
        } catch (e: Exception) {
        }
        return false
    }

    private fun extractAgent(): Path? {
        val resource = AgentLoader::class.java
            .getResourceAsStream("/META-INF/riiilib/agent.jar")
            ?: return null

        return resource.use {
            val temp = Files.createTempFile("riiilib-agent-", ".jar")
            try {
                Files.copy(it, temp, StandardCopyOption.REPLACE_EXISTING)
                temp.toFile().deleteOnExit()
                temp
            } catch (e: Exception) {
                Files.deleteIfExists(temp)
                throw e
            }
        }
    }
}