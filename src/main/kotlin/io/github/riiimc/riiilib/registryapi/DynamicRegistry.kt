package io.github.riiimc.riiilib.registryapi

import io.github.riiimc.riiilib.registryapi.deferred.DeferredDynamicRegistry

/*
object DynamicRegistry {
    fun register() {
        DeferredDynamicRegistry.getRegistries().forEach { registry ->
            registry.entries.forEach { entry ->
                DynamicRegistryAPI.register(registry.registry, entry.value, entry.key)
            }
        }
    }
}

 */