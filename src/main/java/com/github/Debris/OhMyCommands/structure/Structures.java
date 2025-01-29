package com.github.Debris.OhMyCommands.structure;

import com.github.Debris.OhMyCommands.structure.structures.MineshaftStructure;
import com.github.Debris.OhMyCommands.structure.structures.NetherFortressStructure;
import com.github.Debris.OhMyCommands.structure.structures.StrongholdStructure;
import com.github.Debris.OhMyCommands.structure.structures.VillageStructure;
import com.github.Debris.OhMyCommands.unsafe.CreationStructures;
import com.github.Debris.OhMyCommands.unsafe.RICStructures;
import net.minecraft.BiomeGenBase;
import net.xiaoyu233.fml.FishModLoader;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Structures {
    private static final Map<String, Structure> STRUCTURE_MAP = new HashMap<>();

    public static final Structure STRONGHOLD = register(new StrongholdStructure("stronghold"));
    public static final Structure MINESHAFT = register(new MineshaftStructure("mineshaft", 1, 0, 0, List.of(BiomeGenBase.extremeHills, BiomeGenBase.iceMountains, BiomeGenBase.desertHills, BiomeGenBase.forestHills, BiomeGenBase.taigaHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.jungleHills)));
    public static final Structure VILLAGE = register(new VillageStructure("village", 40, 20, 10387312, Arrays.asList(BiomeGenBase.plains, BiomeGenBase.desert)));
    public static final Structure DESERT_PYRAMID = register(new RegionStructure("desertPyramid", 40, 20, 14357617, Arrays.asList(BiomeGenBase.desert, BiomeGenBase.desertHills)));
    public static final Structure JUNGLE_TEMPLE = register(new RegionStructure("jungleTemple", 40, 20, 14357617, Arrays.asList(BiomeGenBase.jungle, BiomeGenBase.jungleHills)));
    public static final Structure WITCH_HUT = register(new RegionStructure("witchHut", 40, 20, 14357617, List.of(BiomeGenBase.swampland)));
    public static final Structure NETHER_FORTRESS = register(new NetherFortressStructure("netherFortress", 1, 0, 0, List.of()));

    public static <T extends Structure> T register(T instance) {
        STRUCTURE_MAP.put(instance.getKey(), instance);
        return instance;
    }

    @Nullable
    public static Structure getFromString(String structure) {
        return STRUCTURE_MAP.get(structure);
    }

    public static String[] generateTabCompletions() {
        return STRUCTURE_MAP.keySet().toArray(String[]::new);
    }

    static {
        Consumer<Structure> registry = Structures::register;

        if (FishModLoader.hasMod("creation")) {
            CreationStructures.register(registry);
        }

        if (FishModLoader.hasMod("rusted_iron_core")) {
            RICStructures.register(registry);
        }
    }
}
