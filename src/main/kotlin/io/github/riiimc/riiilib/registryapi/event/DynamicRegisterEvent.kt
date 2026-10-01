package io.github.riiimc.riiilib.registryapi.event

import io.github.riiimc.riiilib.registryapi.DynamicRegistryAPI
import net.minecraft.core.MappedRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.Event

class DynamicRegisterEvent<T: Any>(val registry: Registry<T>): Event() {

    fun register(location: ResourceLocation, value: T) {
        register(ResourceKey.create(registry.key(), location), value)
    }

    fun register(key: ResourceKey<T>, value: T) {
        val mappedRegistry = registry as MappedRegistry<T>
        DynamicRegistryAPI.register(mappedRegistry, value, key)
    }
}