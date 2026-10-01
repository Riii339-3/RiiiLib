package io.github.riiimc.riiilib.debug

import io.github.riiimc.riiilib.RiiiLib
import io.github.riiimc.riiilib.registryapi.DynamicRegistryAPI
import io.github.riiimc.riiilib.registryapi.deferred.DeferredDynamicRegistry
import io.github.riiimc.riiilib.registryapi.itf.ImmutableRegistryAccessor
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.ResourceLocationArgument
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.RegisterCommandsEvent

@EventBusSubscriber(
    modid = RiiiLib.MODID,
    bus = EventBusSubscriber.Bus.GAME
)
object DebugCommands {

    @JvmStatic
    @SubscribeEvent
    fun registerCommands(event: RegisterCommandsEvent) {
        event.dispatcher.register(
            Commands.literal("riiiregistry")
                .then(
                    Commands.literal("clear")
                        .then(
                            Commands.argument(
                                "registry",
                                ResourceLocationArgument.id()
                            )
                                .executes { ctx ->
                                    val id = ResourceLocationArgument.getId(
                                        ctx,
                                        "registry"
                                    )

                                    val access = ctx.source.registryAccess()

                                    val entry = access.registries()
                                        .filter {
                                            it.key().location() == id
                                        }
                                        .findFirst()
                                        .orElse(null)

                                    if (entry == null) {
                                        ctx.source.sendFailure(
                                            Component.literal(
                                                "Registry not found: $id"
                                            )
                                        )
                                        return@executes 0
                                    }

                                    val mutableAccess =
                                        access as? ImmutableRegistryAccessor

                                    if (mutableAccess == null) {
                                        ctx.source.sendFailure(
                                            Component.literal(
                                                "RegistryAccess is not ImmutableRegistryAccess"
                                            )
                                        )
                                        return@executes 0
                                    }

                                    DynamicRegistryAPI.clear(
                                        access,
                                        entry.key(),
                                        entry.value()
                                    )

                                    ctx.source.sendSuccess(
                                        {
                                            Component.literal(
                                                "Removed registry: $id"
                                            )
                                        },
                                        true
                                    )

                                    1
                                }
                        )
                )
                .then(
                    Commands.literal("add")
                        .then(
                            Commands.argument(
                                "item",
                                ResourceLocationArgument.id()
                            )
                                .executes { ctx ->
                                    val id = ResourceLocationArgument.getId(
                                        ctx,
                                        "item"
                                    )

                                    val access = ctx.source.registryAccess()

                                    val itemRegistry =
                                        BuiltInRegistries.ITEM

                                    DynamicRegistryAPI.allowDynamicRegister(BuiltInRegistries.ITEM)
                                    val item = Item(Item.Properties())

                                    if (item == null) {
                                        ctx.source.sendFailure(
                                            Component.literal(
                                                "Item not found: $id"
                                            )
                                        )
                                        return@executes 0
                                    }

                                    val key = ResourceKey.create(
                                        Registries.ITEM,
                                        id
                                    )

                                    if (itemRegistry.getOptional(key).isPresent) {
                                        ctx.source.sendFailure(
                                            Component.literal(
                                                "Item already exists: $key"
                                            )
                                        )
                                        return@executes 0
                                    }


                                    val holder = DynamicRegistryAPI.createHolder(
                                        BuiltInRegistries.ITEM,
                                        item
                                    )

                                    //DynamicRegistryAPI.add(access, BuiltInRegistries.ITEM, item, key, holder)
                                    DynamicRegistryAPI.register(BuiltInRegistries.ITEM, item, key)
                                    DynamicRegistryAPI.denyDynamicRegister(BuiltInRegistries.ITEM)

                                    1
                                }
                        )
                )
                .then(
                    Commands.literal("deferred")
                        .then(
                            Commands.literal("add").then(
                            Commands.argument(
                                "item",
                                ResourceLocationArgument.id()
                            )
                                .executes { ctx ->
                                    val id = ResourceLocationArgument.getId(
                                        ctx,
                                        "item"
                                    )

                                    val access = ctx.source.registryAccess()

                                    val itemRegistry =
                                        BuiltInRegistries.ITEM

                                    DynamicRegistryAPI.allowDynamicRegister(BuiltInRegistries.ITEM)
                                    val item = Item(Item.Properties())

                                    if (item == null) {
                                        ctx.source.sendFailure(
                                            Component.literal(
                                                "Item not found: $id"
                                            )
                                        )
                                        return@executes 0
                                    }

                                    val key = ResourceKey.create(
                                        Registries.ITEM,
                                        id
                                    )

                                    if (itemRegistry.getOptional(key).isPresent) {
                                        ctx.source.sendFailure(
                                            Component.literal(
                                                "Item already exists: $key"
                                            )
                                        )
                                        return@executes 0
                                    }


                                    val holder = DynamicRegistryAPI.createHolder(
                                        BuiltInRegistries.ITEM,
                                        item
                                    )

                                    //DynamicRegistryAPI.add(access, BuiltInRegistries.ITEM, item, key, holder)
                                    val items =DeferredDynamicRegistry.create(BuiltInRegistries.ITEM.key(), itemRegistry)
                                    DeferredDynamicRegistry.add(items, key, item)
                                    DynamicRegistryAPI.denyDynamicRegister(BuiltInRegistries.ITEM)

                                    1
                                }
                        ))

                )
                .then(
                    Commands.literal("remove")
                        .then(
                            Commands.argument(
                                "registry",
                                ResourceLocationArgument.id()
                            )
                                .then(
                                    Commands.argument(
                                        "entry",
                                        ResourceLocationArgument.id()
                                    )
                                        .executes { ctx ->
                                            val registryId =
                                                ResourceLocationArgument.getId(
                                                    ctx,
                                                    "registry"
                                                )

                                            val entryId =
                                                ResourceLocationArgument.getId(
                                                    ctx,
                                                    "entry"
                                                )

                                            val access = ctx.source.registryAccess()

                                            val registryEntry = access.registries()
                                                .filter {
                                                    it.key().location() == registryId
                                                }
                                                .findFirst()
                                                .orElse(null)

                                            if (registryEntry == null) {
                                                ctx.source.sendFailure(
                                                    Component.literal(
                                                        "Registry not found: $registryId"
                                                    )
                                                )
                                                return@executes 0
                                            }

                                            @Suppress("UNCHECKED_CAST")
                                            val registry =
                                                registryEntry.value() as Registry<Any>

                                            @Suppress("UNCHECKED_CAST")
                                            val key =
                                                ResourceKey.create(
                                                    registryEntry.key() as ResourceKey<Registry<Any>>,
                                                    entryId
                                                )



                                            if (registry.getOptional(key).isEmpty) {
                                                ctx.source.sendFailure(
                                                    Component.literal(
                                                        "Entry not found: $key"
                                                    )
                                                )
                                                return@executes 0
                                            }

                                            DynamicRegistryAPI.allowDynamicRegister(registry)

                                            try {
                                                if (!DynamicRegistryAPI.remove(
                                                        registry,
                                                        key
                                                    )
                                                ) {
                                                    ctx.source.sendFailure(
                                                        Component.literal(
                                                            "Failed to remove entry: $key"
                                                        )
                                                    )
                                                    return@executes 0
                                                }
                                            } finally {
                                                DynamicRegistryAPI.denyDynamicRegister(registry)
                                            }

                                            ctx.source.sendSuccess(
                                                {
                                                    Component.literal(
                                                        "Removed entry: $key"
                                                    )
                                                },
                                                true
                                            )

                                            1
                                        }
                                )
                        )
                )
        )
    }
}