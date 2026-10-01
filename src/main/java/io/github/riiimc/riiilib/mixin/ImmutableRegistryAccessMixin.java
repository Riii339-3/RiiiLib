package io.github.riiimc.riiilib.mixin;

import io.github.riiimc.riiilib.registryapi.itf.ImmutableRegistryAccessor;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

import java.util.HashMap;
import java.util.Map;

@Mixin(RegistryAccess.ImmutableRegistryAccess.class)
public class ImmutableRegistryAccessMixin implements ImmutableRegistryAccessor {
    @Shadow
    @Final
    @Mutable
    private Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> registries;

    @Override
    @Unique
    public void riiimc$removeRegistry(@NotNull ResourceKey<? extends @NotNull Registry<?>> resourceKey, @NotNull Registry<?> registry) {
        Map<ResourceKey<? extends Registry<?>>, Registry<?>> registries = new HashMap<>(this.registries);
        if (registries.containsKey(resourceKey) && registries.containsValue(registry)) registries.remove(resourceKey, registry);
        ImmutableRegistryAccessAccessor accessor = (ImmutableRegistryAccessAccessor) (Object) this;
        accessor.setRegistries(registries);
    }

    @Unique
    @Override
    public <T> void riiimc$addRegistry(@NotNull ResourceKey<? extends @NotNull Registry<? extends T>> resourceKey, @NotNull Registry<? extends T> registry) {
        Map<ResourceKey<? extends Registry<?>>, Registry<?>> registries = new HashMap<>(this.registries);
        registries.put(resourceKey, registry);
        ImmutableRegistryAccessAccessor accessor = (ImmutableRegistryAccessAccessor) (Object) this;
        accessor.setRegistries(registries);
    }

    /*
    @Unique
    @Override
    public RegistryAccess.ImmutableRegistryAccess riiilib$unFreeze() {
        RegistryAccess.ImmutableRegistryAccess accessor = (RegistryAccess.ImmutableRegistryAccess) (Object) this;
        if (accessor instanceof RegistryAccess.Frozen frozen) {
            try {
                RegistryAccess.ImmutableRegistryAccess newAccessor = (RegistryAccess.ImmutableRegistryAccess) frozen;
                return newAccessor;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Unique
    @Override
    public void riiilib$addRegistries() {

    }

    @Unique
    @Override
    public void riiilib$removeRegistries() {

    }

     */
}
