package com.github.Debris.OhMyCommands.api;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BlockBox {
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;

    public BlockBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public BlockBox(int xMin, int zMin, int xMax, int zMax) {
        this.minX = xMin;
        this.minZ = zMin;
        this.maxX = xMax;
        this.maxZ = zMax;
        this.minY = 1;
        this.maxY = 512;
    }

    public BlockBox(Vec3i v1, Vec3i v2) {
        this.minX = Math.min(v1.getX(), v2.getX());
        this.minY = Math.min(v1.getY(), v2.getY());
        this.minZ = Math.min(v1.getZ(), v2.getZ());
        this.maxX = Math.max(v1.getX(), v2.getX());
        this.maxY = Math.max(v1.getY(), v2.getY());
        this.maxZ = Math.max(v1.getZ(), v2.getZ());
    }

    public static BlockBox empty() {
        return new BlockBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public BlockPos getMinCorner() {
        return new BlockPos(this.minX, this.minY, this.minZ);
    }

    public BlockPos getMaxCorner() {
        return new BlockPos(this.maxX, this.maxY, this.maxZ);
    }

    public BlockBox offset(int x, int y, int z) {
        return new BlockBox(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    public void move(int x, int y, int z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
    }

    public boolean intersects(BlockBox box) {
        return this.maxX >= box.minX && this.minX <= box.maxX && this.maxZ >= box.minZ && this.minZ <= box.maxZ && this.maxY >= box.minY && this.minY <= box.maxY;
    }

    public boolean intersectsXZ(int minX, int minZ, int maxX, int maxZ) {
        return this.maxX >= minX && this.minX <= maxX && this.maxZ >= minZ && this.minZ <= maxZ;
    }

    public boolean contains(Vec3i v) {
        return v.getX() >= this.minX && v.getX() <= this.maxX && v.getZ() >= this.minZ && v.getZ() <= this.maxZ && v.getY() >= this.minY && v.getY() <= this.maxY;
    }

    public Vec3i getDimensions() {
        return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
    }

    public void encompass(BlockBox box) {
        this.minX = Math.min(this.minX, box.minX);
        this.minY = Math.min(this.minY, box.minY);
        this.minZ = Math.min(this.minZ, box.minZ);
        this.maxX = Math.max(this.maxX, box.maxX);
        this.maxY = Math.max(this.maxY, box.maxY);
        this.maxZ = Math.max(this.maxZ, box.maxZ);
    }

    public int getXSpan() {
        return this.maxX - this.minX + 1;
    }

    public int getYSpan() {
        return this.maxY - this.minY + 1;
    }

    public int getZSpan() {
        return this.maxZ - this.minZ + 1;
    }

    public Vec3i getCenter() {
        return new Vec3i(this.minX + this.getXSpan() / 2, this.minY + this.getYSpan() / 2, this.minZ + this.getZSpan() / 2);
    }

    public Stream<BlockPos> stream() {
        return StreamSupport.stream(BlockPos.iterate(this.getMinCorner(), this.getMaxCorner()).spliterator(), false);
    }

    public Iterable<BlockPos> iterate() {
        return BlockPos.iterate(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof BlockBox)) {
            return false;
        } else {
            BlockBox blockBox = (BlockBox) o;
            return this.minX == blockBox.minX && this.minY == blockBox.minY && this.minZ == blockBox.minZ && this.maxX == blockBox.maxX && this.maxY == blockBox.maxY && this.maxZ == blockBox.maxZ;
        }
    }

    public int hashCode() {
        return Objects.hash(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public String toString() {
        return "BlockBox{minX=" + this.minX + ", minY=" + this.minY + ", minZ=" + this.minZ + ", maxX=" + this.maxX + ", maxY=" + this.maxY + ", maxZ=" + this.maxZ + '}';
    }
}
