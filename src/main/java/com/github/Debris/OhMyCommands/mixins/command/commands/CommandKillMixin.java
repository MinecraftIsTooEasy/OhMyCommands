package com.github.Debris.OhMyCommands.mixins.command.commands;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CommandKill.class)
public abstract class CommandKillMixin extends CommandBase {

    @Unique
    private static final String item = "item";
    @Unique
    private static final String mob = "mob";
    @Unique
    private static final String orb = "experienceOrb";

    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender iCommandSender, String[] par2ArrayOfStr, CallbackInfo ci) {
        if (par2ArrayOfStr.length == 0) return;
        if (par2ArrayOfStr.length > 1) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        } else {
            WorldServer world = (WorldServer) iCommandSender.getEntityWorld();
            List loadedEntities = world.loadedEntityList;
            List toKill;
            switch (par2ArrayOfStr[0]) {
                case item -> toKill = loadedEntities.stream().filter(o -> o instanceof EntityItem).toList();
                case mob -> toKill = loadedEntities.stream().filter(o -> o instanceof EntityMob).toList();
                case orb -> toKill = loadedEntities.stream().filter(o -> o instanceof EntityXPOrb).toList();
                default -> throw new WrongUsageException(this.getCommandUsage(iCommandSender));
            }
            int y = 0;
            for (Object o : toKill) {
                ((Entity) o).setDead();
                y++;
            }
            notifyAdmins(iCommandSender, y + " " + par2ArrayOfStr[0] + "s were killed");
            ci.cancel();
        }
    }


    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr,
                item, mob, orb) : null;

    }

}
