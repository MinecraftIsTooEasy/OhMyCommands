package com.github.Debris.OhMyCommands.mixins.command;

import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommandBase.class)
public abstract class CommandBaseMixin {
    @Shadow
    public static ServerPlayer getCommandSenderAsPlayer(ICommandSender par0ICommandSender) {
        return null;
    }

    @Inject(method = "getPlayer", at = @At("HEAD"), cancellable = true)
    private static void widen(ICommandSender par0ICommandSender, String par1Str, CallbackInfoReturnable<ServerPlayer> cir) {
        ServerPlayer player = findPlayer(par0ICommandSender, par1Str);
        if (player != null) {
            cir.setReturnValue(player);
        }
    }

    @Unique
    private static ServerPlayer findPlayer(ICommandSender par0ICommandSender, String par1Str) {
        if (par1Str.equals("@s") || par1Str.equals("@p")) {
            return getCommandSenderAsPlayer(par0ICommandSender);
        }
        return null;
    }
}
