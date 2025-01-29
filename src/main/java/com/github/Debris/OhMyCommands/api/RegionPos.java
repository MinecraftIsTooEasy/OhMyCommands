package com.github.Debris.OhMyCommands.api;


public class RegionPos extends Vec3i {
    private final int regionSize;

    public RegionPos(int x, int z, int regionSize) {
        super(x, 0, z);
        this.regionSize = regionSize;
    }
}
