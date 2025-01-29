package com.github.Debris.OhMyCommands.mixins.server;

import net.minecraft.ServerConfigurationManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ServerConfigurationManager.class)
public class ServerConfigurationManagerMixin {
    @Shadow
    private Set ops;

    @Shadow
    @Final
    private MinecraftServer mcServer;

    @Shadow
    private boolean commandsAllowedForAll;

    @Inject(method = "addOp", at = @At("HEAD"))
    private void inject(String par1Str, CallbackInfo ci) {
        this.ops.add(par1Str.trim().toLowerCase());
    }

    /**
     * @author Debris
     * @reason to vanilla
     */
    @Overwrite
    public boolean isPlayerOpped(String par1Str) {
        return this.ops.contains(par1Str.trim().toLowerCase()) || this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(par1Str) || this.commandsAllowedForAll;
    }
}
