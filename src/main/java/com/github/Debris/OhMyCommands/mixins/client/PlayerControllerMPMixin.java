package com.github.Debris.OhMyCommands.mixins.client;

import com.github.Debris.OhMyCommands.command.build.BuildHandler;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class PlayerControllerMPMixin {
    @Shadow
    private EnumGameType currentGameType;

    @Shadow
    @Final
    private Minecraft mc;

    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/EnumGameType;isAdventure()Z"), cancellable = true)
    private void flintAxeCancel(int x, int y, int z, EnumFace face, CallbackInfoReturnable<Boolean> cir) {
        if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItemStack() != null && this.mc.thePlayer.getHeldItemStack().getItem().itemID == Item.axeFlint.itemID) {
            cir.setReturnValue(false);
            BuildHandler.getInstance().setPos1(x, y, z);
            this.mc.thePlayer.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.pos.pos1Set", x, y, z));
        }
    }
}
