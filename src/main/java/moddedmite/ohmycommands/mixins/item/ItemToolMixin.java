package moddedmite.ohmycommands.mixins.item;

import moddedmite.ohmycommands.event.Hooks;
import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemTool.class)
public abstract class ItemToolMixin extends Item {
    @Inject(method = "onItemRightClick", at = @At("HEAD"), cancellable = true)
    private void setPos(EntityPlayer player, float partial_tick, boolean ctrl_is_down, CallbackInfoReturnable<Boolean> cir) {
        if (player.onServer() || this.itemID != Item.axeFlint.itemID || !player.isPlayerInCreative()) {
            return;
        }
        if (Hooks.useFlintAxe(player, partial_tick, ctrl_is_down)) {
            cir.setReturnValue(true);
        }
    }
}
