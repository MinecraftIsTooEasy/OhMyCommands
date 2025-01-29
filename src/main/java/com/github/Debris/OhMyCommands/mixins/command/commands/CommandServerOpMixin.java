package com.github.Debris.OhMyCommands.mixins.command.commands;

import net.minecraft.ChatMessageComponent;
import net.minecraft.CommandServerOp;
import net.minecraft.ICommandSender;
import net.minecraft.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.CommandBase.getPlayer;
import static net.minecraft.CommandBase.notifyAdmins;

@Mixin(CommandServerOp.class)
public class CommandServerOpMixin {
    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender par1ICommandSender, String[] par2ArrayOfStr, CallbackInfo ci) {
        ci.cancel();
        if (par2ArrayOfStr.length == 1 && !par2ArrayOfStr[0].isEmpty()) {
            MinecraftServer.getServer().getConfigurationManager().addOp(par2ArrayOfStr[0]);
            notifyAdmins(par1ICommandSender, "commands.op.success", par2ArrayOfStr[0]);
            getPlayer(par1ICommandSender, par2ArrayOfStr[0]).sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.op.success", par2ArrayOfStr[0]));
        } else {
            throw new WrongUsageException("commands.op.usage");
        }
    }
}
