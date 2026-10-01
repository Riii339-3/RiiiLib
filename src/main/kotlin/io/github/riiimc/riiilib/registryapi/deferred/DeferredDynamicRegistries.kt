package io.github.riiimc.riiilib.registryapi.deferred

import io.github.riiimc.riiilib.registryapi.DynamicRegistryAPI
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey

class DeferredDynamicRegistries<T : Any>(
    val key: ResourceKey<out Registry<T>>,
    val registry: Registry<T>
) {
    private val entries = mutableListOf<DeferredDynamicEntry<T>>()

    fun register(key: ResourceKey<T>, value: T) {
        entries += DeferredDynamicEntry(key, value)
    }

    fun add(
        key: ResourceKey<T>,
        value: T
    ) {
        entries += DeferredDynamicEntry(key, value)
    }

    fun apply() {
        for (entry in entries) {
            DynamicRegistryAPI.allowDynamicRegister(registry)
            DynamicRegistryAPI.register(
                registry,
                entry.value,
                entry.key
            )
            DynamicRegistryAPI.denyDynamicRegister(registry)
        }

        entries.clear()
    }

}