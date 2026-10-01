package io.github.riiimc.riiilib.mixin;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryManager;
import net.neoforged.neoforge.registries.RegistrySnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RegistryManager.class)
public interface RegistryManagerAccessor {

    @Accessor("frozenSnapshot")
    static Map<ResourceLocation, RegistrySnapshot> riiilib$getFrozenSnapshot() {
        throw new AssertionError();
    }

}