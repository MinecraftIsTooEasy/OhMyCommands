package moddedmite.ohmycommands.unsafe;

import mod.mitecreation.world.biome.CTBiomes;
import moddedmite.ohmycommands.structure.RegionStructure;
import moddedmite.ohmycommands.structure.Structure;
import net.minecraft.BiomeGenBase;

import java.util.List;
import java.util.function.Consumer;

public class CreationAccess {
    public static void registerStructure(Consumer<Structure> registry) {
        registry.accept(new RegionStructure("Ruins", 40, 20, 14357617,
                List.of(BiomeGenBase.plains, BiomeGenBase.forest, CTBiomes.TAOYUAN)
        ));
    }
}
