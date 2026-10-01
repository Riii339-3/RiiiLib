package io.github.riiimc.riiilib.registryapi.deferred.event

import io.github.riiimc.riiilib.registryapi.deferred.DeferredDynamicEntry
import io.github.riiimc.riiilib.registryapi.deferred.DeferredDynamicRegistries
import io.github.riiimc.riiilib.registryapi.deferred.DeferredDynamicRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.Event

class DeferredDynamicRegisterEvent<T : Any>(
    private val registry: Registry<T>) : Event() {
    private val entries = mutableListOf<DeferredDynamicEntry<T>>()

    fun register(
        key: ResourceKey<T>,
        value: T
    ) {
        entries += DeferredDynamicEntry(key, value)
    }

    fun register(location: ResourceLocation, value: T) {
        register(ResourceKey.create(registry.key(), location), value)
    }
}