package com.github.Debris.OhMyCommands.mixins.misc;

import com.github.Debris.OhMyCommands.OhMyCommands;
import net.minecraft.EnumGameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnumGameType.class)
public abstract class EnumGameTypeMixin {
    @Shadow
    public static EnumGameType[] values() {
        return new EnumGameType[0];
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static EnumGameType getByID(int par0) {
        if (OhMyCommands.debugMode) {
            System.out.println("getting game type by id: " + par0);
        }
        EnumGameType[] var1 = values();
        int var2 = var1.length;
        for (int var3 = 0; var3 < var2; ++var3) {
            EnumGameType var4 = var1[var3];
            if (var4.id == par0) {
                return var4;
            }
        }
        return EnumGameType.SURVIVAL;
    }
}
