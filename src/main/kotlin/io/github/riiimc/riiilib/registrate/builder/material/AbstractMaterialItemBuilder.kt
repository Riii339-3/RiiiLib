package io.github.riiimc.riiilib.registrate.builder.material

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.AbstractBuilder
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.entry.RegistryEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import io.github.riiimc.riiilib.registrate.RiiiRegistrate
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredHolder

abstract class AbstractMaterialItemBuilder<
        T : Item,
        S : AbstractMaterialItemBuilder<T, S>
        >(
    owner: AbstractRegistrate<*>,
    parent: RiiiRegistrate,
    name: String,
    callback: BuilderCallback,
    private val factory: NonNullFunction<Item.Properties, T>
) : AbstractBuilder<Item, T, RiiiRegistrate, S>(
    owner,
    parent,
    name,
    callback,
    Registries.ITEM
) {
    override fun createEntry(): T {
        return factory.apply(Item.Properties())
    }

    override fun createEntryWrapper(
        delegate: DeferredHolder<Item, T>
    ): RegistryEntry<Item, T> {
        return ItemEntry(owner, delegate)
    }

    override fun register(): ItemEntry<T> {
        return super.register() as ItemEntry<T>
    }
}