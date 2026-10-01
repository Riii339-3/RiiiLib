package io.github.riiimc.riiilib.registrate

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.util.nullness.NonNullFunction
import io.github.riiimc.riiilib.registrate.builder.material.MaterialBuilder
import net.minecraft.world.item.Item
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList

class RiiiRegistrate(modId: String) :
    AbstractRegistrate<RiiiRegistrate>(modId) {

    companion object {

        fun create(modId: String): RiiiRegistrate {
            val ret = RiiiRegistrate(modId)

            val modEventBus = ModList.get()
                .getModContainerById(modId)
                .map(ModContainer::getEventBus)

            modEventBus.ifPresentOrElse(
                ret::registerEventListeners
            ) {

            }

            return ret
        }
    }

    fun <T : Item> material(
        name: String,
        factory: NonNullFunction<Item.Properties, T>
    ): MaterialBuilder<T> {
        return entry(name) { callback ->
            MaterialBuilder(
                this,
                this,
                name,
                callback,
                factory
            )
        }
    }
}