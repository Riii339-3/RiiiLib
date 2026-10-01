package io.github.riiimc.riiilib.registryapi.itf

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey

interface ImmutableRegistryAccessor {
    fun `riiimc$removeRegistry`(resourceKey: ResourceKey<out Registry<*>>, registry: Registry<out Any>)

    fun <T> `riiimc$addRegistry`(resourceKey: ResourceKey<out Registry<out T>>, registry: Registry<out T>)
}