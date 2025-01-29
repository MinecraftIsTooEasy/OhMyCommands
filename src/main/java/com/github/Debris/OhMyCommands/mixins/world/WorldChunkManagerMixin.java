package com.github.Debris.OhMyCommands.mixins.world;

import com.github.Debris.OhMyCommands.api.BiomeAccessor;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(WorldChunkManager.class)
public class WorldChunkManagerMixin implements BiomeAccessor {
    @Shadow
    private GenLayer genBiomes;

    @Override
    public ChunkPosition omc$locateBiome(BiomeGenBase biome, int playerX, int playerZ, int radius) {
        IntCache.resetIntCache();
        int minX = playerX - radius >> 2;
        int minZ = playerZ - radius >> 2;
        int maxX = playerX + radius >> 2;
        int maxZ = playerZ + radius >> 2;
        int lengthX = maxX - minX + 1;
        int lengthZ = maxZ - minZ + 1;
        int[] biomesInts = this.genBiomes.getInts(minX, minZ, lengthX, lengthZ, playerZ);
        List<ChunkPosition> valid = new ArrayList<>();
        for (int var15 = 0; var15 < lengthX * lengthZ; ++var15) {
            int var16 = minX + var15 % lengthX << 2;
            int var17 = minZ + var15 / lengthX << 2;
            BiomeGenBase var18 = BiomeGenBase.biomeList[biomesInts[var15]];
            if (var18 == biome) valid.add(new ChunkPosition(var16, 0, var17));
        }
        ChunkPosition destination = null;
        int dx, dz;
        int distance = 1 << 30;
        for (ChunkPosition chunkPosition : valid) {
            dx = chunkPosition.x - playerX;
            dz = chunkPosition.z - playerZ;
            if (dx * dx + dz * dz < distance) {
                destination = chunkPosition;
                distance = dx * dx + dz * dz;
            }
        }
        return destination;
    }
}
