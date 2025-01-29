package com.github.Debris.OhMyCommands.command.building;

import com.github.Debris.OhMyCommands.api.BlockBox;
import com.github.Debris.OhMyCommands.api.BlockPos;
import net.minecraft.*;

import static net.minecraft.CommandBase.notifyAdmins;

public class BuildingHandler {
    private BlockPos pos1 = null;
    private BlockPos pos2 = null;

    private static final BuildingHandler INSTANCE = new BuildingHandler();

    public static BuildingHandler getInstance() {
        return INSTANCE;
    }

    public void clear() {
        this.pos1 = null;
        this.pos2 = null;
    }

    public void setPos1(BlockPos pos1) {
        this.pos1 = pos1;
    }

    public void setPos1(int x, int y, int z) {
        this.pos1 = new BlockPos(x, y, z);
    }

    public void setPos2(BlockPos pos2) {
        this.pos2 = pos2;
    }

    public void setPos2(int x, int y, int z) {
        this.pos2 = new BlockPos(x, y, z);
    }

    public void homo(ICommandSender iCommandSender, int updateType) {
        if (this.pos1 == null) {
            throw new WrongUsageException("commands.pos.pos1NotSet");
        }
        if (this.pos2 == null) {
            throw new WrongUsageException("commands.pos.pos2NotSet");
        }

        World entityWorld = iCommandSender.getEntityWorld();
        int blockID1 = entityWorld.getBlockId(pos1.getX(), pos1.getY(), pos1.getZ());
        int blockID2 = entityWorld.getBlockId(pos2.getX(), pos2.getY(), pos2.getZ());

        if (blockID1 != blockID2) {
            throw new WrongUsageException("commands.homo.fail.differentBlockID");
        }

        int blockMetadata1 = entityWorld.getBlockMetadata(pos1.getX(), pos1.getY(), pos1.getZ());
        int blockMetadata2 = entityWorld.getBlockMetadata(pos2.getX(), pos2.getY(), pos2.getZ());

        if (blockMetadata1 != blockMetadata2) {
            throw new WrongUsageException("commands.homo.fail.differentMetadata");
        }

        this.fill(iCommandSender, blockID1, blockMetadata1, updateType);
    }

    public void stack(ICommandSender iCommandSender, EnumDirection direction, int stackTimes, int updateType) {
        BlockPos blockpos = this.pos1;
        if (blockpos == null) {
            throw new WrongUsageException("commands.pos.pos1NotSet");
        }
        BlockPos blockpos1 = this.pos2;
        if (blockpos1 == null) {
            throw new WrongUsageException("commands.pos.pos2NotSet");
        }
        BlockBox boundingBox = BlockPos.getBoundingBox(blockpos, blockpos1);
        if (boundingBox.minY < 0 || boundingBox.maxY >= 256) {
            throw new WrongUsageException("commands.world.outOfWorld");
        }
        World world = iCommandSender.getEntityWorld();
        int successCounter = boundingBox.stream().mapToInt(x -> this.stackSingle(world, direction, stackTimes, updateType, boundingBox, x)).sum();
        if (successCounter <= 0) {
            throw new CommandException("commands.stack.failed");
        } else {
            notifyAdmins(iCommandSender, "commands.stack.success", successCounter);
        }
    }

    private int stackSingle(World world, EnumDirection direction, int stackTimes, int updateType, BlockBox blockBox, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int blockID = world.getBlockId(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        final int xStep = direction.dx * blockBox.getXSpan();
        final int yStep = direction.dy * blockBox.getYSpan();
        final int zStep = direction.dz * blockBox.getZSpan();
        int trial = 1;
        int success = 0;
        while (trial <= stackTimes) {
            x += xStep;
            y += yStep;
            z += zStep;
            if (world.setBlock(x, y, z, blockID, metadata, updateType)) {
                success++;
            }
            trial++;
        }
        return success;
    }

    public void fillWithAir(ICommandSender iCommandSender, int updateType) {
        this.fill(iCommandSender, 0, 0, updateType);
    }

    public void fill(ICommandSender iCommandSender, int blockID, int metadata, int updateType) {
        BlockPos blockpos = this.pos1;
        if (blockpos == null) {
            throw new WrongUsageException("commands.pos.pos1NotSet");
        }
        BlockPos blockpos1 = this.pos2;
        if (blockpos1 == null) {
            throw new WrongUsageException("commands.pos.pos2NotSet");
        }
        this.fill(iCommandSender, blockpos, blockpos1, blockID, metadata, updateType);
    }

    public void fill(ICommandSender iCommandSender, BlockPos blockpos, BlockPos blockpos1, int blockID, int metadata, int updateType) {
        BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), Math.min(blockpos.getY(), blockpos1.getY()), Math.min(blockpos.getZ(), blockpos1.getZ()));
        BlockPos blockpos3 = new BlockPos(Math.max(blockpos.getX(), blockpos1.getX()), Math.max(blockpos.getY(), blockpos1.getY()), Math.max(blockpos.getZ(), blockpos1.getZ()));
        if (blockpos2.getY() < 0 || blockpos3.getY() >= 256) {
            throw new WrongUsageException("commands.world.outOfWorld");
        }
        int j = 0;
        World world = iCommandSender.getEntityWorld();
        for (int i1 = blockpos2.getX(); i1 <= blockpos3.getX(); ++i1) {
            for (int j1 = blockpos2.getY(); j1 <= blockpos3.getY(); ++j1) {
                for (int k1 = blockpos2.getZ(); k1 <= blockpos3.getZ(); ++k1) {
                    if (!world.blockExists(i1, j1, k1)) {
                        throw new CommandException("commands.world.outOfWorld");
                    }
                    if (world.setBlock(i1, j1, k1, blockID, metadata, updateType)) {
                        j++;
                    }
                }
            }
        }
        if (j <= 0) {
            throw new CommandException("commands.fill.failed");
        } else {
            notifyAdmins(iCommandSender, "commands.fill.success", j);
        }

    }
}
