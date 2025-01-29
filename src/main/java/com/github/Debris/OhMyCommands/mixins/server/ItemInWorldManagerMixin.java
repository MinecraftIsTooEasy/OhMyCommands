package com.github.Debris.OhMyCommands.mixins.server;

import com.github.Debris.OhMyCommands.OhMyCommands;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInWorldManager.class)
public class ItemInWorldManagerMixin {
    @Shadow
    private EnumGameType gameType;

    @Shadow
    public ServerPlayer thisPlayerMP;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void setGameType(EnumGameType par1EnumGameType) {
        if (OhMyCommands.debugMode) {
            System.out.println("setting game type: " + par1EnumGameType.getName());
        }
        if (Minecraft.inDevMode() || thisPlayerMP.mcServer.getConfigurationManager().isPlayerOpped(thisPlayerMP.username)) {
        } else {
            par1EnumGameType = EnumGameType.SURVIVAL;
        }
        this.gameType = par1EnumGameType;
        par1EnumGameType.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
        this.thisPlayerMP.sendPlayerAbilities();
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public EnumGameType getGameType() {
        return this.gameType;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean isCreative() {
        return this.gameType == EnumGameType.CREATIVE;
    }

    @Inject(method = "tryHarvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/EnumGameType;isAdventure()Z"), cancellable = true)
    private void flintAxeCancel(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItemStack() != null && this.thisPlayerMP.getHeldItemStack().getItem().itemID == Item.axeFlint.itemID) {
            cir.setReturnValue(false);
        }
    }
}
