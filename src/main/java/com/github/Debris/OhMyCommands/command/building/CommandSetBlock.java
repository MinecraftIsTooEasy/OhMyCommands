package com.github.Debris.OhMyCommands.command.building;

import com.github.Debris.OhMyCommands.api.BlockPos;
import com.github.Debris.OhMyCommands.util.NameIDTranslator;
import net.minecraft.*;

import java.util.List;

import static com.github.Debris.OhMyCommands.util.MiscUtil.getTabCompletionCoordinate;
import static com.github.Debris.OhMyCommands.util.NameIDTranslator.getBlockIDByText;

public class CommandSetBlock extends CommandBase {

    public String getCommandName() {
        return "setblock";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override

    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.setblock.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) {
        if (args.length < 4) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        }
        int var3 = iCommandSender.getPlayerCoordinates().posX;
        int var4 = iCommandSender.getPlayerCoordinates().posY;
        int var5 = iCommandSender.getPlayerCoordinates().posZ;
        int range = 30000000;
        var3 = MathHelper.floor_double(func_110666_a(iCommandSender, var3, args[0], -range, range));
        var4 = MathHelper.floor_double(func_110666_a(iCommandSender, var4, args[1], -range, range));
        var5 = MathHelper.floor_double(func_110666_a(iCommandSender, var5, args[2], -range, range));
        int blockID = getBlockIDByText(iCommandSender, args[3]);
        int metadata = 0;
        if (args.length >= 5) {
            metadata = parseIntBounded(iCommandSender, args[4], 0, 15);
        }
        World var8 = iCommandSender.getEntityWorld();

        if (!var8.blockExists(var3, var4, var5)) {
            throw new CommandException("commands.world.outOfWorld");
        } else {
            if (args.length >= 6) {
                if (args[5].equals("destroy")) {
                    BlockBreakInfo info = new BlockBreakInfo(var8, var3, var4, var5);
                    var8.destroyBlock(info, true);
                } else if (args[5].equals("keep") && !var8.isAirBlock(var3, var4, var5)) {
                    throw new CommandException("commands.setblock.noChange");
                }
            }
            if (!var8.setBlock(var3, var4, var5, blockID, metadata, 3)) {
                throw new CommandException("commands.setblock.noChange");
            } else {
                notifyAdmins(iCommandSender, "commands.setblock.success");
            }
        }
    }

    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] args) {
        if (args.length > 0 && args.length <= 3) {
            ChunkCoordinates blockpos = par1ICommandSender.getPlayerCoordinates();
            BlockPos pos = new BlockPos(blockpos.posX, blockpos.posY, blockpos.posZ);
            return getTabCompletionCoordinate(args, 0, pos);
        }
        if (args.length == 4)
            return getListOfStringsMatchingLastWord(args, NameIDTranslator.getBlockTexts());
        return (args.length == 6 ? getListOfStringsMatchingLastWord(args, "replace", "destroy", "keep") : null);
    }
}
