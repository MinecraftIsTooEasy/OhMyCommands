package com.github.Debris.OhMyCommands.mixins.server;

import net.minecraft.DedicatedServer;
import net.minecraft.PropertyManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;

@Mixin(DedicatedServer.class)
public abstract class DedicatedServerMixin extends MinecraftServer {

    @Shadow
    private PropertyManager settings;

    public DedicatedServerMixin(File par1File) {
        super(par1File);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public int func_110455_j() {
        return this.settings.getIntProperty("op-permission-level", 4);
    }
}
