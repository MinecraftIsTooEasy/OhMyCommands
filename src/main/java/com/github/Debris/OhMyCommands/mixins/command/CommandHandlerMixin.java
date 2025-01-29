package com.github.Debris.OhMyCommands.mixins.command;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.CommandHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CommandHandler.class)
public class CommandHandlerMixin {
    @ModifyExpressionValue(method = "executeCommand", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z", ordinal = 8))
    private boolean soften(boolean original) {
        return true;
    }
}
