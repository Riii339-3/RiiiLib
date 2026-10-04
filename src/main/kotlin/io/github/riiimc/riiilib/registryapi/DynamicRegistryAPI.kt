package io.github.riiimc.riiilib.registryapi

import io.github.riiimc.riiilib.mixin.HolderReferenceAccessor
import io.github.riiimc.riiilib.mixin.MappedRegistryAccessor
import io.github.riiimc.riiilib.mixin.RegistryManagerAccessor
import io.github.riiimc.riiilib.registryapi.itf.DynamicRegistry
import io.github.riiimc.riiilib.registryapi.itf.ImmutableRegistryAccessor
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.Reference2IntMaps
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap
import net.minecraft.core.Holder
import net.minecraft.core.MappedRegistry
import net.minecraft.core.RegistrationInfo
import net.minecraft.core.Registry
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceKey

object DynamicRegistryAPI {
    private val ADDITIONAL_REGISTRIES = mutableMapOf<ResourceKey<out Registry<out Any>>, Registry<*>>()
    @Suppress("UNCHECKED_CAST")
    fun clear(
        registryAccess: RegistryAccess,
        resourceKey: ResourceKey<out Registry<out Any>>,
        registry: Registry<out Any>
    ) {
        // RegistryAccess 側から削除
        val access = registryAccess as ImmutableRegistryAccessor

        access.`riiimc$removeRegistry`(
        resourceKey,
        registry
        )

        // Registry 自体の中身も削除
        val mappedRegistry =
            registry as? MappedRegistry<Any>
                ?: return

        val accessor =
            mappedRegistry as MappedRegistryAccessor<Any>

        accessor.setEntryById(
            ObjectArrayList()
        )

        accessor.setEntryByKey(
            HashMap()
        )

        accessor.setEntryByLocation(
            HashMap()
        )

        accessor.setEntryByValue(
            HashMap()
        )
        accessor.setEntryToId(
            Reference2IntMaps.synchronize(
                Reference2IntOpenHashMap()
            )
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun add(registryAccess: RegistryAccess, registry: Registry<out Any>) {
        val access = registryAccess as ImmutableRegistryAccessor
        val resourceKey = registry.key()
        access.`riiimc$addRegistry`(
            resourceKey,
            registry
        )

        val mappedRegistry =
            registry as? MappedRegistry<Any>
                ?: return
        val accessor = mappedRegistry as MappedRegistryAccessor<Any>

        ADDITIONAL_REGISTRIES[resourceKey] = registry

        val byId = accessor.entryById
        val byKey = accessor.entryByKey
        val byLocation = accessor.entryByLocation
        val byValue = accessor.entryByValue
        val byIdToId = accessor.entryToId

        val anyKey = resourceKey as ResourceKey<Any>
        val holder = registry.holders().findFirst().orElseThrow()
        val id = byId.size

        byId.add(holder)
        byKey[anyKey] = holder
        byLocation[resourceKey.location()] = holder
        byValue[holder.value()] = holder
        byIdToId.put(holder.value(), id)
        /*
        accessor.entryById = byId
        accessor.entryByKey = byKey
        accessor.entryByLocation = byLocation
        accessor.entryByValue = byValue
        accessor.entryToId = byIdToId

         */
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> add(
        registryAccess: RegistryAccess,
        registry: Registry<T>,
        obj: T,
        resourceKey: ResourceKey<T>,
        holder: Holder.Reference<T>
    ) {
        val access = registryAccess as ImmutableRegistryAccessor

        val registryKey = registry.key()

        access.`riiimc$addRegistry`(
            registryKey,
            registry
        )

        val mappedRegistry =
            registry as? MappedRegistry<T>
                ?: return

        val accessor = mappedRegistry as MappedRegistryAccessor<T>

        val byId = accessor.entryById
        val byKey = accessor.entryByKey
        val byLocation = accessor.entryByLocation
        val byValue = accessor.entryByValue
        val byIdToId = accessor.entryToId

        val id = byId.size

        (holder as HolderReferenceAccessor<T>).`riiilib$bindKey`(resourceKey)

        byId.add(holder)

        byKey[resourceKey] = holder

        byLocation[resourceKey.location()] = holder

        byValue[obj] = holder

        byIdToId.put(obj, id)
    }

    fun <T> createHolder(
        registry: Registry<T>,
        value: T
    ): Holder.Reference<T> {
        val mappedRegistry = registry as MappedRegistry<T>
        return mappedRegistry.createIntrusiveHolder(value)
    }

    fun <T> allowDynamicRegister(registry: Registry<T>) {
        val mappedRegistry = registry as MappedRegistry<T> as? DynamicRegistry ?: return
        mappedRegistry.`riiilib$setAllowDynamicRegister`(true)
    }
    fun <T> denyDynamicRegister(registry: Registry<T>) {
        val mappedRegistry = registry as MappedRegistry<T> as? DynamicRegistry ?: return
        mappedRegistry.`riiilib$setAllowDynamicRegister`(false)
    }

    fun <T : Any> register(
        registry: Registry<T>,
        obj: T,
        resourceKey: ResourceKey<T>
    ) {
        allowDynamicRegister(registry)
        val mappedRegistry = registry as? MappedRegistry<T> ?: return
        if (mappedRegistry !is DynamicRegistry) return

        mappedRegistry.register(
            resourceKey,
            obj,
            RegistrationInfo.BUILT_IN
        )
        denyDynamicRegister(registry)
    }
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> remove(
        registry: Registry<T>,
        resourceKey: ResourceKey<T>
    ): Boolean {
        val mappedRegistry =
            registry as? MappedRegistry<T>
                ?: return false

        val accessor =
            mappedRegistry as MappedRegistryAccessor<T>

        val holder =
            accessor.entryByKey.remove(resourceKey)
                ?: return false

        val value = holder.value()
        val id = accessor.entryToId.removeInt(value)

        accessor.entryByLocation.remove(resourceKey.location())
        accessor.entryByValue.remove(value)
        accessor.registrationInfos.remove(resourceKey)

        if (id >= 0) {
            accessor.entryById.removeAt(id)

            // IDを詰めたので後続EntryのIDを更新
            for (i in id until accessor.entryById.size) {
                val entry = accessor.entryById.get(i)
                val entryValue = entry.value()

                accessor.entryToId.put(entryValue, i)
            }
        }

        return true
    }

}