package com.github.Debris.OhMyCommands.mixins.item;

import com.github.Debris.OhMyCommands.command.build.BuildHandler;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemAxe.class)
public abstract class ItemAxeMixin extends ItemTool {

    protected ItemAxeMixin(int par1, Material material) {
        super(par1, material);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        if (this.itemID != Item.axeFlint.itemID || !player.isPlayerInCreative()) {
            return super.onItemRightClick(player, partial_tick, ctrl_is_down);
        }
        if (player.rightClickCancelled()) return false;
        player.getPlayerController().setUseButtonDelay();
        RaycastCollision rc = player.getSelectedObject(partial_tick, false);
        if (rc == null) {
            return false;
        }
        if (rc.isBlock()) {
            int x = rc.block_hit_x;
            int y = rc.block_hit_y;
            int z = rc.block_hit_z;
            BuildHandler.getInstance().setPos2(x, y, z);
            player.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.pos.pos2Set", x, y, z));
        }
        return false;
    }
}
