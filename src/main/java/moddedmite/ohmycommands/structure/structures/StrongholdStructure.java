package moddedmite.ohmycommands.structure.structures;

import moddedmite.ohmycommands.structure.Structure;
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
