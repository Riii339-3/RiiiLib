package io.github.riiimc.riiilib.interfaces.item

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.SwordItem

interface IDestroyerItem {
    fun isItem(): Boolean {
        return this is Item
    }

    fun isSword(): Boolean {
        return this is SwordItem
    }

    fun destroy(target: LivingEntity): Boolean {
        return false
    }
}