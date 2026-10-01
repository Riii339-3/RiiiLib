package io.github.riiimc.riiilib.registrate.builder.material

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.AbstractBuilder
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.entry.RegistryEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import io.github.riiimc.riiilib.registrate.RiiiRegistrate
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.registries.DeferredHolder

class MaterialBuilder<T : Item>(
    owner: AbstractRegistrate<*>,
    parent: RiiiRegistrate,
    name: String,
    callback: BuilderCallback,
    private val factory: NonNullFunction<Item.Properties, T>
) : AbstractBuilder<
        Item,
        T,
        RiiiRegistrate,
        MaterialBuilder<T>
        >(
    owner,
    parent,
    name,
    callback,
    Registries.ITEM
) {

    private var materialType: MaterialType? = null
    //private val parts = mutableListOf<String>()

    fun materialType(type: MaterialType): MaterialBuilder<T> {
        this.materialType = type
        tag(ProviderType.ITEM_TAGS, ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots")))
        tag(ProviderType.ITEM_TAGS, ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/${name}")))
        return this
    }

    fun tagItem(vararg parts: String): MaterialBuilder<T> {
        //val ret = mutableListOf<MaterialTagItemBuilder<T>>()
        parts.forEach {part ->
            val builder = MaterialTagItemBuilder(owner, parent, "${name}/${part}", callback, factory, name).tag()
            builder.register()
            //ret.add(builder)
        }
        return this
    }

    fun tagItem(vararg parts: String, factory: NonNullFunction<Item.Properties, T>): MaterialBuilder<T> {
        parts.forEach { part ->
            val builder = MaterialTagItemBuilder(owner, parent, "${name}/${part}", callback, factory, name).tag()
            builder.register()
        }
        return this
    }

    fun tagItem(vararg parts : String, factories: List<NonNullFunction<Item.Properties, T>>): MaterialBuilder<T> {
        if (parts.size != factories.size) {
            throw IllegalArgumentException("Parts and properties must have the same size")
        }
        for (i in parts.indices) {
            val builder = MaterialTagItemBuilder(owner, parent, "${name}/${parts[i]}", callback, factories[i], name).tag()
            builder.register()
        }
        return this
    }

    fun tagBlockItem(vararg parts: String): MaterialBuilder<T> {
        parts.forEach { part ->
            val builder = MaterialBlockBuilder(owner, parent, part, callback, { properties ->
                Block(properties)
            }).blockItem()
        }
        return this
    }


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