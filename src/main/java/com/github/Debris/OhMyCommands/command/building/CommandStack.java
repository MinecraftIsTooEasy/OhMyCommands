package com.github.Debris.OhMyCommands.command.building;

import net.minecraft.*;

public class CommandStack extends CommandBase {

    @Override
    public String getCommandName() {
        return "stack";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.stack.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) {
        if (args.length > 2) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        }
        int stackTimes = 1;
        int updateType = 2;
        if (args.length >= 1) {
            stackTimes = parseInt(iCommandSender, args[0]);
        }
        if (args.length == 2) {
            updateType = parseInt(iCommandSender, args[1]);
        }
        EnumDirection direction;
        float rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        if (rotationPitch < -45.0f) direction = EnumDirection.UP;
        else if (rotationPitch > 45.0f) direction = EnumDirection.DOWN;
        else direction = Minecraft.getMinecraft().thePlayer.getDirectionFromYaw();
        BuildingHandler.getInstance().stack(iCommandSender, direction, stackTimes, updateType);
    }
}
