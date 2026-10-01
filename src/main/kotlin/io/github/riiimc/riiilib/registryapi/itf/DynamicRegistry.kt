package io.github.riiimc.riiilib.registryapi.itf

interface DynamicRegistry {
    fun `riiilib$getAllowDynamicRegister`(): Boolean
    fun `riiilib$setAllowDynamicRegister`(value: Boolean)
}