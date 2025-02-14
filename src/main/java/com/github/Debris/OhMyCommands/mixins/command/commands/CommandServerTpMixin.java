package com.github.Debris.OhMyCommands.mixins.command.commands;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.CommandBase.*;

@Mixin(CommandServerTp.class)
public class CommandServerTpMixin {
    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender par1ICommandSender, String[] par2ArrayOfStr, CallbackInfo ci) {
        ci.cancel();

        if (par2ArrayOfStr.length < 1) {
            throw new WrongUsageException("commands.tp.usage");
        } else {
            ServerPlayer var3;

            if (par2ArrayOfStr.length != 2 && par2ArrayOfStr.length != 4) {
                var3 = getCommandSenderAsPlayer(par1ICommandSender);
            } else {
                var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);

                if (var3 == null) {
                    throw new PlayerNotFoundException();
                }
            }

            if (par2ArrayOfStr.length != 3 && par2ArrayOfStr.length != 4) {
                if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2) {
                    ServerPlayer var11 = getPlayer(par1ICommandSender, par2ArrayOfStr[par2ArrayOfStr.length - 1]);

                    if (var11 == null) {
                        throw new PlayerNotFoundException();
                    }

                    if (var11.worldObj != var3.worldObj) {
                        notifyAdmins(par1ICommandSender, "commands.tp.notSameDimension", new Object[0]);
                        return;
                    }

                    var3.mountEntity(null);
                    var3.playerNetServerHandler.setPlayerLocation(var11.posX, var11.posY, var11.posZ, var11.rotationYaw, var11.rotationPitch);
                    notifyAdmins(par1ICommandSender, "commands.tp.success", new Object[]{var3.getEntityName(), var11.getEntityName()});
                }
            } else if (var3.worldObj != null) {
                int var4 = par2ArrayOfStr.length - 3;
                int bound = 30000000;
                double var5 = func_110666_a(par1ICommandSender, var3.posX, par2ArrayOfStr[var4++], -bound, bound);
                double var7 = func_110665_a(par1ICommandSender, var3.posY, par2ArrayOfStr[var4++], 0, 0);
                double var9 = func_110666_a(par1ICommandSender, var3.posZ, par2ArrayOfStr[var4++], -bound, bound);
                var3.mountEntity(null);
                var3.setPositionAndUpdate(var5, var7, var9);
                notifyAdmins(par1ICommandSender, "commands.tp.success.coordinates", new Object[]{var3.getEntityName(), Double.valueOf(var5), Double.valueOf(var7), Double.valueOf(var9)});
            }
        }
    }
}
