package com.github.Debris.OhMyCommands.command.building;

import net.minecraft.ChatMessageComponent;
import net.minecraft.ChunkCoordinates;
import net.minecraft.ICommandSender;

public class CommandPos2 extends CommandPos1 {

    @Override
    public String getCommandName() {
        return "pos2";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.pos2.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) {
        ChunkCoordinates blockpos = iCommandSender.getPlayerCoordinates();
        int x = blockpos.posX;
        int y = blockpos.posY;
        int z = blockpos.posZ;
        BuildingHandler.getInstance().setPos2(x, y, z);
        iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.pos.pos2Set", x, y, z));
    }
}

