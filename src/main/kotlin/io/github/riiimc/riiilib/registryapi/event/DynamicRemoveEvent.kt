package io.github.riiimc.riiilib.registryapi.event

import io.github.riiimc.riiilib.registryapi.DynamicRegistryAPI
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.Event

class DynamicRemoveEvent<T: Any>(val registry: Registry<T>): Event() {
    fun remove(key: ResourceKey<T>) {
        DynamicRegistryAPI.remove(registry, key)
    }
    fun remove(location: ResourceLocation) {
        remove(ResourceKey.create(registry.key(), location))
    }
}