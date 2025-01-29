package com.github.Debris.OhMyCommands.structure;

import com.github.Debris.OhMyCommands.api.ChunkPos;
import com.github.Debris.OhMyCommands.api.RegionPos;
import com.github.Debris.OhMyCommands.util.ChunkRand;
import net.minecraft.BiomeGenBase;
import net.minecraft.ChunkPosition;
import net.minecraft.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RegionStructure extends Structure {
    private final int spacing;
    private final int separation;
    private final int salt;
    private final List<BiomeGenBase> validBiomes;
    public static final int LOCATE_RADIUS = 100;

    public RegionStructure(String key, int spacing, int separation, int salt, List<BiomeGenBase> validBiomes) {
        super(key);
        this.spacing = spacing;
        this.separation = separation;
        this.salt = salt;
        this.validBiomes = validBiomes;
    }

    public int getSalt() {
        return salt;
    }

    public int getSeparation() {
        return separation;
    }

    public int getSpacing() {
        return spacing;
    }

    public List<BiomeGenBase> getValidBiomes() {
        return validBiomes;
    }

    @Override
    public ChunkPosition locateAt(World world, int blockX, int blockY, int blockZ) {
        ChunkPos chunkHere = new ChunkPos(blockX >> 4, blockZ >> 4);
        RegionPos regionHere = chunkHere.toRegionPos(this.getSpacing());
        int radius = 0;
        Random rand = new Random();
        while (radius < LOCATE_RADIUS) {
            for (int rx = -radius; rx <= radius; rx++) {
                for (int rz = -radius; rz <= radius && Math.max(Math.abs(rx), Math.abs(rz)) == radius; rz++) {
                    int regionX = regionHere.getX() + rx;
                    int regionZ = regionHere.getZ() + rz;
                    ChunkPosition chunkPosition = this.getInRegion(rand, world, regionX, regionZ);
                    if (chunkPosition == null) continue;
                    return chunkPosition;
                }
            }
            radius++;
        }
        return null;
    }

    @Override
    public int getRadiusFeedback() {
        return LOCATE_RADIUS * this.spacing;
    }

    @Nullable
    public ChunkPosition getInRegion(Random rand, World world, int regionX, int regionZ) {
        ChunkRand.setRegionSeed(rand, world.getSeed(), regionX, regionZ, this.getSalt());
        int chunkX = regionX * this.getSpacing() + rand.nextInt(this.getSpacing() - this.getSeparation());
        int chunkZ = regionZ * this.getSpacing() + rand.nextInt(this.getSpacing() - this.getSeparation());
        if (this == Structures.DESERT_PYRAMID || this == Structures.JUNGLE_TEMPLE) {
            double distance_from_world_spawn = world.getDistanceFromWorldSpawn(chunkX * 16, chunkZ * 16);
            if (distance_from_world_spawn < 2000.0) return null;
        }
        if (!world.getWorldChunkManager().areBiomesViable(chunkX * 16, chunkZ * 16, 0, this.getValidBiomes()))
            return null;
        return new ChunkPosition(chunkX << 4, 0, chunkZ << 4);
    }
}
