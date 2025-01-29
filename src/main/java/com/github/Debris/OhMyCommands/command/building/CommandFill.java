package com.github.Debris.OhMyCommands.command.building;

import com.github.Debris.OhMyCommands.api.BlockPos;
import com.github.Debris.OhMyCommands.util.NameIDTranslator;
import net.minecraft.*;

import java.util.List;

import static com.github.Debris.OhMyCommands.util.MiscUtil.getTabCompletionCoordinate;
import static com.github.Debris.OhMyCommands.util.MiscUtil.parseBlockPos;

public class CommandFill extends CommandBase {
    public String getCommandName() {
        return "xfill";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.fill.usage";
    }

    public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
        if (args.length < 7 || args.length > 9) {
            throw new WrongUsageException("commands.fill.usage");
        }
        int blockID = NameIDTranslator.getBlockIDByText(iCommandSender, args[6]);
        int metadata = 0;
        int updateType = 2;
        if (args.length > 7) {
            metadata = parseIntBounded(iCommandSender, args[7], 0, 15);
        }
        if (args.length > 8) {
            updateType = parseInt(iCommandSender, args[8]);
        }
        BlockPos blockpos = parseBlockPos(iCommandSender, args, 0);
        BlockPos blockpos1 = parseBlockPos(iCommandSender, args, 3);
        BuildingHandler.getInstance().fill(iCommandSender, blockpos, blockpos1, blockID, metadata, updateType);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args) {
        if (args.length > 0 && args.length <= 3) {
            ChunkCoordinates blockpos = iCommandSender.getPlayerCoordinates();
            BlockPos pos = new BlockPos(blockpos.posX, blockpos.posY, blockpos.posZ);
            return getTabCompletionCoordinate(args, 0, pos);
        }
        if (args.length > 3 && args.length <= 6) {
            ChunkCoordinates blockpos = iCommandSender.getPlayerCoordinates();
            BlockPos pos = new BlockPos(blockpos.posX, blockpos.posY, blockpos.posZ);
            return getTabCompletionCoordinate(args, 3, pos);
        }
        if (args.length == 7) return getListOfStringsMatchingLastWord(args, NameIDTranslator.getBlockTexts());
        return null;
    }
}