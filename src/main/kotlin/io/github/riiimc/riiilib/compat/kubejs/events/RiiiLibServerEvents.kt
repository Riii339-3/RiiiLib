package io.github.riiimc.riiilib.compat.kubejs.events

import dev.latvian.mods.kubejs.event.EventGroup
import dev.latvian.mods.kubejs.event.EventHandler
import dev.latvian.mods.kubejs.event.EventTargetType
import dev.latvian.mods.kubejs.event.KubeEvent
import dev.latvian.mods.kubejs.event.TargetedEventHandler
import dev.latvian.mods.kubejs.plugin.builtin.event.ServerEvents
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import java.util.function.Supplier

object RiiiLibServerEvents {
    val GROUP = EventGroup.of("RiiiLibServerEvents")

    val REGISTRY =
        GROUP.server(
            "register",
            Supplier { RiiiLibRegisterEvent::class.java }
        ).requiredTarget(EventTargetType.REGISTRY)
}