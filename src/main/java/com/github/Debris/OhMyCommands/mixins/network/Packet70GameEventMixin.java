package com.github.Debris.OhMyCommands.mixins.network;

import com.github.Debris.OhMyCommands.OhMyCommands;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.Packet70GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Packet70GameEvent.class)
public class Packet70GameEventMixin {
    @ModifyExpressionValue(method = "<init>(II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z"))
    private boolean soften(boolean original) {
        if (OhMyCommands.debugMode) {
            System.out.println("sending packet 70: game event");
        }
        return true;
    }
}
