package io.github.riiimc.riiilib.datalib.registry

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller

class DataLibRegistryListener: SimpleJsonResourceReloadListener(GsonBuilder().create(), "riiilib/registry")  {
    override fun apply(p0: Map<ResourceLocation, JsonElement>, p1: ResourceManager, p2: ProfilerFiller) {
        p0.forEach { (location, json) ->

        }
    }



}