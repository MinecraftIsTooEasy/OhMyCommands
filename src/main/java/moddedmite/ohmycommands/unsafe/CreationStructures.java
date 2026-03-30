package moddedmite.ohmycommands.unsafe;

import moddedmite.ohmycommands.structure.RegionStructure;
import moddedmite.ohmycommands.structure.Structure;
import mod.mitecreation.world.biome.CTBiomes;
import net.minecraft.BiomeGenBase;

import java.util.List;
import java.util.function.Consumer;

public class CreationStructures {
    public static void register(Consumer<Structure> registry) {
        registry.accept(new RegionStructure("Ruins", 40, 20, 14357617,
                List.of(BiomeGenBase.plains, BiomeGenBase.forest, CTBiomes.TAOYUAN)
        ));
    }
}
