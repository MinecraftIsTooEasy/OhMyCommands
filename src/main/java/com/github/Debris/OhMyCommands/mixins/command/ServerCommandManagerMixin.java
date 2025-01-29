package com.github.Debris.OhMyCommands.mixins.command;

import com.github.Debris.OhMyCommands.command.*;
import com.github.Debris.OhMyCommands.command.building.*;
import net.minecraft.CommandHandler;
import net.minecraft.ServerCommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommandManager.class)
public class ServerCommandManagerMixin extends CommandHandler {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectInit(CallbackInfo callbackInfo) {
        this.registerCommand(new CommandSummon());
        this.registerCommand(new CommandLocate());
        this.registerCommand(new CommandLocateBiome());
        this.registerCommand(new CommandCurse());
        this.registerCommand(new CommandDimension());
        this.registerCommand(new CommandFill());
        this.registerCommand(new CommandSetBlock());
        this.registerCommand(new CommandPos1());
        this.registerCommand(new CommandPos2());
        this.registerCommand(new CommandSet());
        this.registerCommand(new CommandHomo());
        this.registerCommand(new CommandStack());
        this.registerCommand(new CommandForceLoad());
        this.registerCommand(new CommandQuality());
        this.registerCommand(new CommandDamage());
        this.registerCommand(new CommandSetWorldSpawn());
    }
}
