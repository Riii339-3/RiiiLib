package io.github.riiimc.riiilib.datalib.registry

import dev.latvian.mods.kubejs.script.ScriptType
import io.github.riiimc.riiilib.compat.kubejs.events.RiiiLibRegisterEvent
import io.github.riiimc.riiilib.compat.kubejs.events.RiiiLibServerEvents
import io.github.riiimc.riiilib.mixin.RegistryManagerAccessor
import io.github.riiimc.riiilib.registryapi.deferred.DeferredDynamicRegistry
import net.minecraft.core.Registry
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceKey
import net.minecraft.server.packs.resources.PreparableReloadListener
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.profiling.ProfilerFiller
import net.neoforged.neoforge.registries.RegistryManager
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

class DataLibFinished(
    private val access: RegistryAccess
) : PreparableReloadListener {

    override fun reload(
        barrier: PreparableReloadListener.PreparationBarrier,
        resourceManager: ResourceManager,
        preparationProfiler: ProfilerFiller,
        reloadProfiler: ProfilerFiller,
        backgroundExecutor: Executor,
        gameExecutor: Executor
    ): CompletableFuture<Void?> {

        return barrier.wait(Unit).thenRunAsync({
            access.registries().forEach { registry ->
                @Suppress("UNCHECKED_CAST")
                val registryKey =
                    registry.key as ResourceKey<Registry<*>>
                RiiiLibServerEvents.REGISTRY.post(
                    ScriptType.SERVER,
                    registryKey,
                    RiiiLibRegisterEvent(
                        registry.value
                    )
                )
            }

            DeferredDynamicRegistry
                .getRegistries()
                .forEach { it.apply() }

        }, gameExecutor)
    }
}