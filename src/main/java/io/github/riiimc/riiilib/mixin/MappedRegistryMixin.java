package io.github.riiimc.riiilib.mixin;

import io.github.riiimc.riiilib.registryapi.itf.DynamicRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin implements DynamicRegistry {

    @Unique
    private boolean riiilib$allowDynamicRegister;

    @Override
    public boolean riiilib$getAllowDynamicRegister() {
        return riiilib$allowDynamicRegister;
    }

    @Override
    public void riiilib$setAllowDynamicRegister(boolean b) {
        riiilib$allowDynamicRegister = b;
    }

    @Inject(method = "unfreeze", at = @At("TAIL"))
    private void riiilib$unfreeze(CallbackInfo ci) {
        riiilib$setAllowDynamicRegister(false);
    }

    @Inject(method = "freeze", at = @At("TAIL"))
    private <T> void riiilib$freeze(CallbackInfoReturnable<Registry<T>> cir) {
        riiilib$setAllowDynamicRegister(true);
    }

    @Inject(method = "validateWrite*", at = @At("HEAD"), cancellable = true)
    private void riiilib$validateWrite(CallbackInfo ci) {
        if (riiilib$allowDynamicRegister) {
            ci.cancel();
        }
    }
}
