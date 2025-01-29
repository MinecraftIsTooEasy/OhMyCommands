package com.github.Debris.OhMyCommands.structure.structures;

import com.github.Debris.OhMyCommands.structure.RegionStructure;
import com.github.Debris.OhMyCommands.util.ChunkRand;
import net.minecraft.BiomeGenBase;
import net.minecraft.ChunkPosition;
import net.minecraft.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MineshaftStructure extends RegionStructure {
    public MineshaftStructure(String key, int spacing, int separation, int salt, List<BiomeGenBase> validBiomes) {
        super(key, spacing, separation, salt, validBiomes);
    }

    @Override
    public @Nullable ChunkPosition getInRegion(Random rand, World world, int regionX, int regionZ) {
        ChunkRand.setCarverSeed(rand, world.getSeed(), regionX, regionZ);
        rand.nextInt();
        if (rand.nextFloat() >= 0.005f) return null;
        if (!world.getWorldChunkManager().areBiomesViable(regionX * 16, regionZ * 16, 0, this.getValidBiomes()))
            return null;
        boolean farFromSpawn = rand.nextInt(80) < Math.max(Math.abs(regionX), Math.abs(regionZ));
        if (!farFromSpawn) return null;
        return new ChunkPosition(regionX << 4, 0, regionZ << 4);
    }
}
