package io.github.riiimc.riiilib.registryapi.itf

import net.minecraft.core.RegistryAccess

interface UnFreezableAccess {
    fun `riiilib$unFreeze`(): RegistryAccess.ImmutableRegistryAccess?
    fun `riiilib$addRegistries`()

    fun `riiilib$removeRegistries`()
}