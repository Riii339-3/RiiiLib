package io.github.riiimc.riiilib.compat

import net.neoforged.fml.ModList

object CompatUtils {
    fun isModLoaded(modId: String): Boolean {
        return ModList.get().isLoaded(modId)
    }
}