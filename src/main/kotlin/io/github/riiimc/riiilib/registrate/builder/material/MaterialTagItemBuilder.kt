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
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredHolder

class MaterialTagItemBuilder<T:Item>(
    owner: AbstractRegistrate<*>,
    parent: RiiiRegistrate,
    name: String,
    callback: BuilderCallback,
    private var factory: NonNullFunction<Item.Properties, T>,
    private val material: String
):
    AbstractMaterialItemBuilder<T, MaterialTagItemBuilder<T>>(
        owner,
        parent,
        name,
        callback,
        factory
    ) {
    fun tag(): MaterialTagItemBuilder<T> {
        tag(name)
        return this
    }
    fun tag(tag: String): MaterialTagItemBuilder<T> {
        tag("c", tag)
        return this
    }
    fun tag(namespace: String, path: String ): MaterialTagItemBuilder<T> {
        tag(ProviderType.ITEM_TAGS, ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, path)))
        tag(ProviderType.ITEM_TAGS, ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, "${path}/${material}")))
        return this
    }
}