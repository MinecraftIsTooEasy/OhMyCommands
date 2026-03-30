package moddedmite.ohmycommands.api;

import net.minecraft.BiomeGenBase;
import net.minecraft.ChunkPosition;

public interface BiomeAccessor {
    ChunkPosition omc$locateBiome(BiomeGenBase biome, int playerX, int playerZ, int radius);
}
