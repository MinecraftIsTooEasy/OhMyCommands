package com.github.Debris.OhMyCommands.command.building;

import com.github.Debris.OhMyCommands.util.NameIDTranslator;
import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;
import net.minecraft.WrongUsageException;

import java.util.List;

import static com.github.Debris.OhMyCommands.util.NameIDTranslator.getBlockIDByText;

public class CommandSet extends CommandBase {

    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.set.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        }
        int metadata = 0;
        int updateType = 2;
        if (args.length > 1) {
            metadata = parseIntBounded(iCommandSender, args[1], 0, 15);
        }
        if (args.length > 2) {
            updateType = parseInt(iCommandSender, args[2]);
        }
        BuildingHandler.getInstance().fill(iCommandSender, getBlockIDByText(iCommandSender, args[0]), metadata, updateType);
    }


    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] args) {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, NameIDTranslator.getBlockTexts());
        return null;
    }

}
