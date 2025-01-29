package com.github.Debris.OhMyCommands.api;

import org.jetbrains.annotations.Unmodifiable;

@Unmodifiable
public class Vec3i implements Comparable<Vec3i> {
    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    protected int x;
    protected int y;
    protected int z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec3i)) {
            return false;
        }
        Vec3i vec3i = (Vec3i) o;
        if (this.getX() != vec3i.getX()) {
            return false;
        }
        if (this.getY() != vec3i.getY()) {
            return false;
        }
        return this.getZ() == vec3i.getZ();
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    @Override
    public int compareTo(Vec3i vec3i) {
        if (this.getY() == vec3i.getY()) {
            if (this.getZ() == vec3i.getZ()) {
                return this.getX() - vec3i.getX();
            }
            return this.getZ() - vec3i.getZ();
        }
        return this.getY() - vec3i.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    protected Vec3i setX(int x) {
        this.x = x;
        return this;
    }

    protected Vec3i setY(int y) {
        this.y = y;
        return this;
    }

    protected Vec3i setZ(int z) {
        this.z = z;
        return this;
    }

    /**
     * {@return another Vec3i whose coordinates have the parameter x, y, and z
     * added to the coordinates of this vector}
     *
     * <p>This method always returns an immutable object.
     */
    public Vec3i add(int x, int y, int z) {
        if (x == 0 && y == 0 && z == 0) {
            return this;
        }
        return new Vec3i(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    /**
     * {@return another Vec3i whose coordinates have the coordinates of {@code vec}
     * added to the coordinates of this vector}
     *
     * <p>This method always returns an immutable object.
     */
    public Vec3i add(Vec3i vec) {
        return this.add(vec.getX(), vec.getY(), vec.getZ());
    }

    /**
     * {@return another Vec3i whose coordinates have the coordinates of {@code vec}
     * subtracted from the coordinates of this vector}
     *
     * <p>This method always returns an immutable object.
     */
    public Vec3i subtract(Vec3i vec) {
        return this.add(-vec.getX(), -vec.getY(), -vec.getZ());
    }

    /**
     * {@return a vector with all components multiplied by {@code scale}}
     *
     * @implNote This can return the same vector if {@code scale} equals {@code 1}.
     */
    public Vec3i multiply(int scale) {
        if (scale == 1) {
            return this;
        }
        if (scale == 0) {
            return ZERO;
        }
        return new Vec3i(this.getX() * scale, this.getY() * scale, this.getZ() * scale);
    }

    /**
     * {@return a vector which is offset by {@code distance} in {@code direction} direction}
     *
     * @implNote This can return the same vector if {@code distance} equals {@code 0}.
     */

    public Vec3i crossProduct(Vec3i vec) {
        return new Vec3i(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    /**
     * {@return whether the distance between here and {@code vec} is less than {@code distance}}
     */
    public boolean isWithinDistance(Vec3i vec, double distance) {
        return this.getSquaredDistance(vec) < distance * distance;
    }

    /**
     * {@return whether the distance between here and {@code pos} is less than {@code distance}}
     */
    public boolean isWithinDistance(Position pos, double distance) {
        return this.getSquaredDistance(pos) < distance * distance;
    }

    /**
     * {@return the squared distance between here (center) and {@code vec}}
     *
     * @see #getSquaredDistance(double, double, double)
     * @see #getSquaredDistanceFromCenter(double, double, double)
     */
    public double getSquaredDistance(Vec3i vec) {
        return this.getSquaredDistance(vec.getX(), vec.getY(), vec.getZ());
    }

    /**
     * {@return the squared distance between here and {@code pos}}
     */
    public double getSquaredDistance(Position pos) {
        return this.getSquaredDistanceFromCenter(pos.getX(), pos.getY(), pos.getZ());
    }

    public double getSquaredDistanceFromCenter(double x, double y, double z) {
        double d = (double) this.getX() + 0.5 - x;
        double e = (double) this.getY() + 0.5 - y;
        double f = (double) this.getZ() + 0.5 - z;
        return d * d + e * e + f * f;
    }

    /**
     * {@return the squared distance between here and {@code (x, y, z)}}
     * This is equivalent to {@code Vec3d.of(this).squaredDistanceTo(x, y, z)}.
     */
    public double getSquaredDistance(double x, double y, double z) {
        double d = (double) this.getX() - x;
        double e = (double) this.getY() - y;
        double f = (double) this.getZ() - z;
        return d * d + e * e + f * f;
    }

    /**
     * {@return the Manhattan distance between here and {@code vec}}
     *
     * <p>Manhattan distance, also called taxicab distance or snake distance, is the
     * distance measured as the sum of the absolute differences of their coordinates.
     * For example, the Manhattan distance between {@code (0, 0, 0)} and {@code (1, 1, 1)}
     * is {@code 3}.
     */
    public int getManhattanDistance(Vec3i vec) {
        float f = Math.abs(vec.getX() - this.getX());
        float g = Math.abs(vec.getY() - this.getY());
        float h = Math.abs(vec.getZ() - this.getZ());
        return (int) (f + g + h);
    }

    public String toString() {
        return this.getX() + ", " + this.getY() + ", " + this.getZ();
    }
}


