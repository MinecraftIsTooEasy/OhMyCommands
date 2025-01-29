package com.github.Debris.OhMyCommands.mixins.block;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockCommandBlock.class)
public class BlockCommandBlockMixin {
    @Inject(method = "onBlockActivated", at = @At("HEAD"), cancellable = true)
    private void canBeUsed(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float dx, float dy, float dz, CallbackInfoReturnable<Boolean> cir) {
        TileEntityCommandBlock tile_entity = (TileEntityCommandBlock)world.getBlockTileEntity(x, y, z);
        if (tile_entity != null) {
            player.displayGUIEditSign(tile_entity);
        }
        cir.setReturnValue(true);
    }
}
