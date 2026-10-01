package io.github.riiimc.riiilib.registrate.builder.material

import com.sun.jna.platform.win32.COM.util.Factory
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.entry.RegistryEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import io.github.riiimc.riiilib.registrate.RiiiRegistrate
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredHolder

class MaterialTagBlockItemBuilder<T: BlockItem>(
    owner: AbstractRegistrate<*>,
    parent: RiiiRegistrate,
    name: String,
    callback: BuilderCallback,
    private var factory: NonNullFunction<Item.Properties, T>,
):
    AbstractMaterialItemBuilder<T, MaterialTagBlockItemBuilder<T>>(
        owner,
        parent,
        name,
        callback,
        factory
    ) {

    fun create(): MaterialTagBlockItemBuilder<T> {
        create { p0 ->  Block(p0) }
        return this
    }

    fun create(
        factory: NonNullFunction<BlockBehaviour.Properties, Block>
    ): MaterialTagBlockItemBuilder<T> {
        MaterialBlockBuilder(
            owner,
            parent,
            name,
            callback,
            factory
        ).blockItem()

        return this
    }
}