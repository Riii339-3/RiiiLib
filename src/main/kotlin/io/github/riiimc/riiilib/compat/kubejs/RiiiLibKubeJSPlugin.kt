package io.github.riiimc.riiilib.compat.kubejs

import dev.latvian.mods.kubejs.event.EventGroupRegistry
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin
import dev.latvian.mods.kubejs.script.ScriptManager
import dev.latvian.mods.kubejs.script.ScriptType
import io.github.riiimc.riiilib.compat.kubejs.events.RiiiLibRegisterEvent
import io.github.riiimc.riiilib.compat.kubejs.events.RiiiLibServerEvents
import net.neoforged.neoforge.registries.RegistryManager
import java.rmi.registry.Registry

class RiiiLibKubeJSPlugin : KubeJSPlugin {

    override fun registerEvents(registry: EventGroupRegistry) {
        registry.register(RiiiLibServerEvents.GROUP)
    }

    override fun afterScriptsLoaded(manager: ScriptManager) {
        when (manager.scriptType) {
            ScriptType.SERVER -> {
                /*
                RiiiLibServerEvents.REGISTRY.post(
                    ScriptType.SERVER,
                    RiiiLibRegisterEvent()
                )

                 */
            }
            ScriptType.CLIENT -> {
            }
            else -> {}
        }
    }
}