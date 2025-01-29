package com.github.Debris.OhMyCommands.command;

import net.minecraft.*;

public class CommandDamage extends CommandBase {

    @Override
    public String getCommandName() {
        return "damage";
    }

    @Override
    public String getCommandUsage(ICommandSender ICommandSender) {
        return "commands.damage.usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length < 1) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        }
        ItemStack heldItemStack = getCommandSenderAsPlayer(iCommandSender).getHeldItemStack();
        if (heldItemStack == null) {
            throw new CommandException("commands.fail.emptyHanded");
        }
        if (!heldItemStack.isItemStackDamageable()) {
            throw new CommandException("commands.damage.damageable");
        }
        heldItemStack.setItemDamage(parseInt(iCommandSender, strings[0]));
    }
}
