package com.github.Debris.OhMyCommands.command;

import com.github.Debris.OhMyCommands.api.OMCChunkProviderServer;
import com.github.Debris.OhMyCommands.util.MiscUtil;
import net.minecraft.*;

import java.util.List;

public class CommandForceLoad extends CommandBase {
    @Override
    public String getCommandName() {
        return "forceLoad";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "command.forceLoad.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) {
        if (args.length != 1 && args.length != 3) {
            throw new WrongUsageException("commands.forceLoad.usage");
        }
        WorldServer worldServer = iCommandSender.getEntityWorld().getAsWorldServer();
        ChunkProviderServer theChunkProviderServer = worldServer.theChunkProviderServer;
        int dimensionId = worldServer.getDimensionId();
        OMCChunkProviderServer omcChunkProviderServer = (OMCChunkProviderServer) theChunkProviderServer;
        if (args[0].equals("clear")) {// 1
            omcChunkProviderServer.omc$ClearChunks();
            notifyAdmins(iCommandSender, "commands.forceLoad.clear", dimensionId);
            return;
        } else if (args[0].equals("get")) {
            String content = String.format("dimension %d: ", dimensionId);
            String data = omcChunkProviderServer.omc$GetChunks().stream().map(MiscUtil::chunkCoordinatesFromLong).map(x -> String.format("[%d,%d]", x[0], x[1])).toList().toString();
            iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(content + data));
        } else if (!args[0].equals("remove") && !args[0].equals("add")) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        }
        int x, y;
        if (args.length > 1) {
            x = parseInt(iCommandSender, args[1]);
            y = parseInt(iCommandSender, args[2]);
        } else {
            x = iCommandSender.getPlayerCoordinates().posX >> 4;
            y = iCommandSender.getPlayerCoordinates().posZ >> 4;
        }
        long chunk = ChunkCoordIntPair.chunkXZ2Int(x, y);
        if (args[0].equals("remove")) {
            omcChunkProviderServer.omc$RemoveChunk(chunk);
            theChunkProviderServer.unloadChunksIfNotNearSpawn(x, y);
            notifyAdmins(iCommandSender, "commands.forceLoad.remove.success", x, y, dimensionId);
        } else {
            omcChunkProviderServer.omc$AddChunk(chunk);
            theChunkProviderServer.loadChunk(x, y);
            notifyAdmins(iCommandSender, "commands.forceLoad.add.success", x, y, dimensionId);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) return getListOfStringsMatchingLastWord(par2ArrayOfStr, "add", "clear", "get", "remove");
        return null;
    }
}
