package com.github.Debris.OhMyCommands;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;

public class OhMyCommands implements ModInitializer {
    public static final boolean debugMode = false;

    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain("ohmycommands");
    }
}