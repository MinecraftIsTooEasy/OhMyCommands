package com.github.Debris.OhMyCommands.command;

import net.minecraft.*;

public class CommandSetWorldSpawn extends CommandBase {

    @Override
    public String getCommandName() {
        return "setWorldSpawn";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.setWorldSpawn.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) {
        if (args.length != 0 && args.length != 3) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        }
        int x, y, z;
        if (args.length > 0) {
            x = parseInt(iCommandSender, args[0]);
            y = parseInt(iCommandSender, args[1]);
            z = parseInt(iCommandSender, args[2]);
        } else {
            ChunkCoordinates playerCoordinates = iCommandSender.getPlayerCoordinates();
            x = playerCoordinates.posX;
            y = playerCoordinates.posY;
            z = playerCoordinates.posZ;
        }
        World entityWorld = iCommandSender.getEntityWorld();
        ChunkCoordinates spawnPointBefore = entityWorld.getSpawnPoint();
        if (spawnPointBefore.getDistanceSquaredToChunkCoordinates(new ChunkCoordinates(x,y,z)) == 0) {
            notifyAdmins(iCommandSender, "commands.setWorldSpawn.duplicate");
            return;
        }
        entityWorld.setSpawnLocation(x, y, z);
        if (entityWorld.getSpawnPoint().getDistanceSquaredToChunkCoordinates(spawnPointBefore) > 0) {
            notifyAdmins(iCommandSender, "commands.setWorldSpawn.success", x, y, z);
        } else {
            notifyAdmins(iCommandSender, "commands.setWorldSpawn.fail");
        }
    }
}
