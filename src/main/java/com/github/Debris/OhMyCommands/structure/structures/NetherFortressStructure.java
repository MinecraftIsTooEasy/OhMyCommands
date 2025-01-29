package com.github.Debris.OhMyCommands.structure.structures;

import com.github.Debris.OhMyCommands.structure.RegionStructure;
import com.github.Debris.OhMyCommands.util.ChunkRand;
import net.minecraft.BiomeGenBase;
import net.minecraft.ChunkPosition;
import net.minecraft.World;

import java.util.List;
import java.util.Random;

public class NetherFortressStructure extends RegionStructure {
    public NetherFortressStructure(String key, int spacing, int separation, int salt, List<BiomeGenBase> validBiomes) {
        super(key, spacing, separation, salt, validBiomes);
    }

    @Override
    public ChunkPosition getInRegion(Random rand, World world, int regionX, int regionZ) {
        ChunkRand.setWeakSeed(rand, world.getSeed(), regionX, regionZ);
        rand.nextInt();
        if (rand.nextInt(3) == 0 && (regionX == (regionX >> 4 << 4) + 4 + rand.nextInt(8)) && (regionZ == (regionZ >> 4 << 4) + 4 + rand.nextInt(8)))
            return new ChunkPosition(regionX << 4, 0, regionZ << 4);
        return null;
    }
}
