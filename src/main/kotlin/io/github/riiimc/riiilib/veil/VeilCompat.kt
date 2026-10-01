package io.github.riiimc.riiilib.veil

import io.github.riiimc.riiilib.compat.CompatUtils

object VeilCompat {
    fun isLoaded(): Boolean {
        return CompatUtils.isModLoaded("veil")
    }
}