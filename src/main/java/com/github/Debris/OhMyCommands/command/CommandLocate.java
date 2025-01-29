package com.github.Debris.OhMyCommands.command;

import com.github.Debris.OhMyCommands.structure.Structure;
import com.github.Debris.OhMyCommands.structure.Structures;
import com.github.Debris.OhMyCommands.util.MiscUtil;
import net.minecraft.*;

import java.util.List;

public class CommandLocate extends CommandBase {
    @Override
    public String getCommandName() {
        return "locate";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.locate.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length != 1) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        }

        String string = strings[0];

        Structure structure = Structures.getFromString(string);
        if (structure == null) {
            throw new WrongUsageException("commands.locate.structureNotExist");
        }

        ChunkCoordinates playerPosition = iCommandSender.getPlayerCoordinates();
        World world = iCommandSender.getEntityWorld();

        if (!structure.checkCondition(iCommandSender)) return;

        ChunkPosition chunkPosition = structure.locateAt(world, playerPosition.posX, playerPosition.posY, playerPosition.posZ);
        String info;
        if (chunkPosition != null) {
            int x = chunkPosition.x + 4;
            int z = chunkPosition.z + 4;
            info = Translator.getFormatted("commands.locate.found", string, x, z);
            MiscUtil.copyToClipBoard("/tp " + x + " " + playerPosition.posY + " " + z);
        } else {
            info = Translator.getFormatted("commands.locate.notFound", string, structure.getRadiusFeedback());
        }
        iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(info));
    }

    private String[] optionsCache = null;

    @Override
    public List<?> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (optionsCache == null) {
            optionsCache = Structures.generateTabCompletions();
        }
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, optionsCache) : null;
    }

}
