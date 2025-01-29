package com.github.Debris.OhMyCommands.mixins.command.commands;

import net.minecraft.CommandTime;
import net.minecraft.ICommandSender;
import net.minecraft.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.CommandBase.notifyAdmins;
import static net.minecraft.CommandBase.parseIntWithMin;

@Mixin(CommandTime.class)
public abstract class CommandTimeMixin {

    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender par1ICommandSender, String[] par2ArrayOfStr, CallbackInfo ci) {
        ci.cancel();
        if (par2ArrayOfStr.length > 1) {
            int var3;
            if (par2ArrayOfStr[0].equals("set")) {
                if (par2ArrayOfStr[1].equals("day")) {
                    var3 = 0;
                } else if (par2ArrayOfStr[1].equals("night")) {
                    var3 = 12500;
                } else {
                    var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
                }
                for (int dim = 0; dim < MinecraftServer.getServer().worldServers.length; ++dim) {
                    MinecraftServer.getServer().worldServers[dim].setTotalWorldTime(var3);
                }
                notifyAdmins(par1ICommandSender, "commands.time.set", var3);
                return;
            }
            if (par2ArrayOfStr[0].equals("add")) {
                var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
                for (int dim = 0; dim < MinecraftServer.getServer().worldServers.length; ++dim) {
                    MinecraftServer.getServer().worldServers[dim].setTotalWorldTime(var3);
                }
                notifyAdmins(par1ICommandSender, "commands.time.added", var3);
                return;
            }
        }
        throw new WrongUsageException("commands.time.usage");
    }
}
