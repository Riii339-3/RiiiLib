package io.github.riiimc.riiilib.registryapi.deferred

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey

object DeferredDynamicRegistry {

    private val registries =
        mutableListOf<DeferredDynamicRegistries<*>>()

    fun <T : Any> create(
        key: ResourceKey<out Registry<T>>,
        registry: Registry<T>
    ): DeferredDynamicRegistries<T> {
        val deferred = DeferredDynamicRegistries(key, registry)
        registries += deferred
        return deferred
    }

    fun <T : Any> add(
        deferred: DeferredDynamicRegistries<T>,
        key: ResourceKey<T>,
        value: T
    ) {
        deferred.add(key, value)
    }

    fun getRegistries(): List<DeferredDynamicRegistries<*>> = registries
}