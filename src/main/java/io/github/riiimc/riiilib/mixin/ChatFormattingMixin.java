package io.github.riiimc.riiilib.mixin;

import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ChatFormatting.class)
public class ChatFormattingMixin {
    @Shadow
    @Final
    @Mutable
    private static ChatFormatting[] $VALUES;

    public ChatFormattingMixin(
            String name,
            int ordinal,
            String name2,
            char code,
            int dare,
            Integer color
    ) {

    }

    @Inject(
            method = "<clinit>",
            at = @At(value = "FIELD", target = "Lnet/minecraft/ChatFormatting;$VALUES:[Lnet/minecraft/ChatFormatting;",
            shift = At.Shift.AFTER)
    )
    private static void addChatFormatting(CallbackInfo ci) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 2);
        $VALUES[ordinal] = (ChatFormatting) (Object) (new ChatFormattingMixin("RIIILIB", ordinal, "RIIILIB", '?', 1, 1024));
        ordinal++;
        $VALUES[ordinal] = (ChatFormatting) (Object) (new ChatFormattingMixin("RIIILIB_W", ordinal, "RIIILIB_W", '!', 2, 25565));

    }

}
