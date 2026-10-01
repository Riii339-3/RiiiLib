package io.github.riiimc.riiilib.example

import io.github.riiimc.riiilib.RiiiLib.Companion.REGISTRUM
import io.github.riiimc.riiilib.registrate.builder.material.MaterialType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item

object AllExample {
    val TEST_MATERIAL = REGISTRUM.material("test_material", ::Item).materialType(MaterialType.INGOT).register()

    fun init() {}
}