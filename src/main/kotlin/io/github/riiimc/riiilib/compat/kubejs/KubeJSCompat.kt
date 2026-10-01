package io.github.riiimc.riiilib.compat.kubejs

import io.github.riiimc.riiilib.compat.CompatUtils

object KubeJSCompat {
    fun init() {

    }

    fun isLoaded(): Boolean {
        return CompatUtils.isModLoaded("kubejs")
    }
}