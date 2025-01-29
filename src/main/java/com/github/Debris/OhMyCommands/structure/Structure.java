package com.github.Debris.OhMyCommands.structure;

import net.minecraft.ChunkPosition;
import net.minecraft.ICommandSender;
import net.minecraft.World;

import javax.annotation.Nullable;

public abstract class Structure {
    private final String key;

    public Structure(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    @Nullable
    public abstract ChunkPosition locateAt(World world, int blockX, int blockY, int blockZ);

    public abstract int getRadiusFeedback();

    public boolean checkCondition(ICommandSender iCommandSender) {
        return true;
    }
}
