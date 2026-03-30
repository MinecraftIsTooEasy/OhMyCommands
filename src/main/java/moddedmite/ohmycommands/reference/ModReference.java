package moddedmite.ohmycommands.reference;

import net.xiaoyu233.fml.FishModLoader;

public class ModReference {
    public static final String CREATION = "creation";
    public static final String RIC = "rusted_iron_core";

    public static boolean hasMod(String modId) {
        return FishModLoader.hasMod(modId);
    }
}
