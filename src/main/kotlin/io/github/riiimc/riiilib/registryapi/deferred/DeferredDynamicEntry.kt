package io.github.riiimc.riiilib.registryapi.deferred

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey

data class DeferredDynamicEntry<T : Any>(
    val key: ResourceKey<T>,
    val value: T
)