package io.github.riiimc.riiilib.mixin;

import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(MappedRegistry.class)
public interface MappedRegistryAccessor<T> {
    @Accessor("byId")
    ObjectList<Holder.Reference<T>> getEntryById();

    @Accessor("byId")
    @Mutable
    void setEntryById(ObjectList<Holder.Reference<T>> entryById);

    @Accessor("byKey")
    Map<ResourceKey<T>, Holder.Reference<T>> getEntryByKey();

    @Accessor("byKey")
    @Mutable
    void setEntryByKey(Map<ResourceKey<T>, Holder.Reference<T>> entryByKey);

    @Accessor("byLocation")
    Map<ResourceLocation, Holder.Reference<T>> getEntryByLocation();

    @Accessor("byLocation")
    @Mutable
    void setEntryByLocation(Map<ResourceLocation, Holder.Reference<T>> entryByLocation);

    @Accessor("byValue")
    Map<T, Holder.Reference<T>> getEntryByValue();

    @Accessor("byValue")
    @Mutable
    void setEntryByValue(Map<T, Holder.Reference<T>> entryByValue);

    @Accessor("toId")
    Reference2IntMap<T> getEntryToId();

    @Accessor("toId")
    @Mutable
    void setEntryToId(Reference2IntMap<T> entryToId);

    @Accessor("registrationInfos")
    @Mutable
    Map<ResourceKey<T>, RegistrationInfo> getRegistrationInfos();
}
