package com.github.Debris.OhMyCommands.util;

import java.util.Random;

public class ChunkRand {
    public static final long RegionA = 341873128712L;
    public static final long RegionB = 132897987541L;

    public static long setCarverSeed(Random rand, long worldSeed, int chunkX, int chunkZ) {
        rand.setSeed(worldSeed);
        long a = rand.nextLong();
        long b = rand.nextLong();
        long seed = (long) chunkX * a ^ (long) chunkZ * b ^ worldSeed;
        rand.setSeed(seed);
        return seed;
    }

    public static long setRegionSeed(Random rand, long worldSeed, int regionX, int regionZ, int salt) {
        long seed = (long) regionX * 341873128712L + (long) regionZ * 132897987541L + worldSeed + (long) salt;
        rand.setSeed(seed);
        return seed;
    }

    public static long setWeakSeed(Random rand, long worldSeed, int chunkX, int chunkZ) {
        int sX = chunkX >> 4;
        int sZ = chunkZ >> 4;
        long seed = (long) (sX ^ sZ << 4) ^ worldSeed;
        rand.setSeed(seed);
        return seed;
    }
}
