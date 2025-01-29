package com.github.Debris.OhMyCommands.structure.structures;

import com.github.Debris.OhMyCommands.structure.Structure;
import net.minecraft.ChunkPosition;
import net.minecraft.World;
import net.minecraft.server.MinecraftServer;

public class StrongholdStructure extends Structure {
    public StrongholdStructure(String key) {
        super(key);
    }

    @Override
    public ChunkPosition locateAt(World world, int blockX, int blockY, int blockZ) {
        return world.findClosestStructure("Stronghold", blockX, blockY, blockZ);
    }

    @Override
    public int getRadiusFeedback() {
        return MinecraftServer.getServer().getEntityWorld().max_block_xz;
    }
}
