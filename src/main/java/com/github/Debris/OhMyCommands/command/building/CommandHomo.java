package com.github.Debris.OhMyCommands.command.building;

import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;
import net.minecraft.WrongUsageException;

public class CommandHomo extends CommandBase {

    @Override
    public String getCommandName() {
        return "homo";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.homo.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) {
        if (args.length > 1) {
            throw new WrongUsageException("commands.fill.usage");
        }
        int updateType = 2;
        if (args.length == 1) {
            updateType = parseInt(iCommandSender, args[0]);
        }
        BuildingHandler buildingHandler = BuildingHandler.getInstance();
        buildingHandler.homo(iCommandSender, updateType);
    }
}
