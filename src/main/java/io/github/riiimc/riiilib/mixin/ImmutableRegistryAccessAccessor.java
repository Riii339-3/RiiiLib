package io.github.riiimc.riiilib.mixin;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RegistryAccess.ImmutableRegistryAccess.class)
public interface ImmutableRegistryAccessAccessor {
    @Accessor("registries")
    Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> getRegistries();

    @Accessor("registries")
    @Mutable
    void setRegistries(Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> registries);
}
