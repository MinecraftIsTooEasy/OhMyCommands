package com.github.Debris.OhMyCommands.command.building;

import net.minecraft.ChatMessageComponent;
import net.minecraft.ChunkCoordinates;
import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;

public class CommandPos1 extends CommandBase {

    @Override
    public String getCommandName() {
        return "pos1";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.pos1.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) {
        ChunkCoordinates blockpos = iCommandSender.getPlayerCoordinates();
        int x = blockpos.posX;
        int y = blockpos.posY;
        int z = blockpos.posZ;
        BuildingHandler.getInstance().setPos1(x, y, z);
        iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.pos.pos1Set", x, y, z));
    }
}
