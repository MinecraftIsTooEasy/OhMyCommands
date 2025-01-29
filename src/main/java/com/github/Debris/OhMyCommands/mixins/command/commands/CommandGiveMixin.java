package com.github.Debris.OhMyCommands.mixins.command.commands;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.CommandBase.*;

@Mixin(CommandGive.class)
public class CommandGiveMixin {
    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender iCommandSender, String[] args, CallbackInfo ci) {
        ci.cancel();
        if (args.length >= 2) {

            ServerPlayer player = getPlayer(iCommandSender, args[0]);
            int itemID = parseIntWithMin(iCommandSender, args[1], 1);
            int num = 1;
            int subType = 0;

            if (Item.itemsList[itemID] == null) {
                throw new NumberInvalidException("commands.give.notFound", itemID);
            } else {
                if (args.length >= 3) {
                    num = parseIntBounded(iCommandSender, args[2], 1, 64);
                }

                if (args.length >= 4) {
                    subType = parseInt(iCommandSender, args[3]);
                }

                ItemStack itemStack = new ItemStack(itemID, num, subType);
                EntityItem entityItem = player.dropPlayerItem(itemStack);
                entityItem.delayBeforeCanPickup = 0;
                notifyAdmins(iCommandSender, "commands.give.success", Item.itemsList[itemID].getItemStackDisplayName(itemStack), num, player.getEntityName());
            }
        } else {
            throw new WrongUsageException("commands.give.usage");
        }
    }
}
