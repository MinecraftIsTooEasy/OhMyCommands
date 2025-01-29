package com.github.Debris.OhMyCommands.mixins.gui;

import com.github.Debris.OhMyCommands.command.building.BuildingHandler;
import net.minecraft.GuiButton;
import net.minecraft.GuiIngameMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public class GuiIngameMenuMixin {
    @Inject(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;loadWorld(Lnet/minecraft/WorldClient;)V"))
    private void saveStatus(GuiButton par1GuiButton, CallbackInfo ci) {
        BuildingHandler.getInstance().clear();
    }
}
