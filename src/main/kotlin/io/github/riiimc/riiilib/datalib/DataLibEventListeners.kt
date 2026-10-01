package io.github.riiimc.riiilib.datalib

import io.github.riiimc.riiilib.RiiiLib
import io.github.riiimc.riiilib.datalib.registry.DataLibFinished
import io.github.riiimc.riiilib.datalib.registry.DataLibRegistryListener
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.AddReloadListenerEvent

@EventBusSubscriber(modid = RiiiLib.MODID, bus = EventBusSubscriber.Bus.MOD)
object DataLibEventListeners {
    @JvmStatic
    @SubscribeEvent
    fun reload(event: AddReloadListenerEvent) {
        event.addListener(DataLibRegistryListener())
        event.addListener(DataLibFinished(event.registryAccess))
    }
}
