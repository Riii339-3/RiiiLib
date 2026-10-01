package io.github.riiimc.riiilib.compat.kubejs.events

import dev.latvian.mods.kubejs.event.KubeEvent
import io.github.riiimc.riiilib.registryapi.DynamicRegistryAPI
import io.github.riiimc.riiilib.registryapi.deferred.event.DeferredDynamicRegisterEvent
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

class RiiiLibRegisterEvent<T: Any>(
    val registry: Registry<T>
) : KubeEvent {

    fun create(
        id: ResourceLocation,
        obj: T
    ) {
        val key = ResourceKey.create(registry.key(), id)

        DynamicRegistryAPI.register(
            registry,
            obj,
            key
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun createUnchecked(
        id: ResourceLocation,
        obj: Any
    ) {
        val registry = registry as Registry<Any>
        val key = ResourceKey.create(registry.key(), id)

        DynamicRegistryAPI.register(
            registry,
            obj,
            key
        )
    }
}