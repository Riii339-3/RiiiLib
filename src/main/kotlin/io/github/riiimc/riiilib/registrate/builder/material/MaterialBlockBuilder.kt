package io.github.riiimc.riiilib.registrate.builder.material

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.AbstractBuilder
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.entry.RegistryEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import io.github.riiimc.riiilib.registrate.RiiiRegistrate
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredHolder

class MaterialBlockBuilder<T : Block>(
    owner: AbstractRegistrate<*>,
    parent: RiiiRegistrate,
    name: String,
    callback: BuilderCallback,
    private val factory: NonNullFunction<BlockBehaviour.Properties, T>
) : AbstractBuilder<
        Block,
        T,
        RiiiRegistrate,
        MaterialBlockBuilder<T>
        >(
    owner,
    parent,
    name,
    callback,
    Registries.BLOCK
) {
    val blockBuilder = this

    fun blockItem(): MaterialTagBlockItemBuilder<BlockItem> {
        val blockBuilder = this

        blockBuilder.register()

        return MaterialTagBlockItemBuilder(
            owner,
            parent,
            name,
            callback,
            NonNullFunction { properties ->
                BlockItem(blockBuilder.entry, properties)
            }
        ).also {
            it.register()
        }
    }

    override fun createEntry(): T {
        return factory.apply(BlockBehaviour.Properties.of())
    }

    override fun createEntryWrapper(
        delegate: DeferredHolder<Block, T>
    ): RegistryEntry<Block, T> {
        return BlockEntry(owner, delegate)
    }


    override fun register(): BlockEntry<T> {
        return super.register() as BlockEntry<T>
    }
}