package com.github.Debris.OhMyCommands.command;

import com.github.Debris.OhMyCommands.util.NameIDTranslator;
import net.minecraft.*;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class CommandCurse extends CommandBase {

    public String getCommandName() {
        return "curse";
    }

    public String getCommandUsage(ICommandSender ICommandSender) {
        return "commands.curse.usage";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public void processCommand(ICommandSender iCommandSender, String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("commands.curse.usage");
        }
        ServerPlayer player = getPlayer(iCommandSender, args[0]);
        if (args[1].equals("clear")) {
            if (!player.is_cursed) {
                throw new CommandException("commands.curse.clear.notCursed");
            }
            ((WorldServer) player.worldObj).removeCursesFromPlayer(player);
            notifyAdmins(iCommandSender, "commands.curse.clear.success", player.getEntityName());
            return;
        }
        if (!args[1].equals("add")) {
            throw new WrongUsageException("commands.curse.usage");
        }

        Curse toAdd = NameIDTranslator.getCurseByText(iCommandSender, args[2]);

        player = getCommandSenderAsPlayer(iCommandSender);
        EntityWitch temp = new EntityWitch(player.worldObj);
        player.worldObj.getAsWorldServer().addCurse(player, temp, toAdd, 0);
        player.learnCurseEffect();
        iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("commands.curse.add.success" + toAdd.getTitle()).setColor(EnumChatFormatting.WHITE));
    }

    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1)
            return CommandEffect.getListOfStringsMatchingLastWord(par2ArrayOfStr, getAllUsernames());
        if (par2ArrayOfStr.length == 2) return getListOfStringsMatchingLastWord(par2ArrayOfStr, "add", "clear");
        if (par2ArrayOfStr.length == 3 && par2ArrayOfStr[1].equals("add"))
            return getListOfStringsMatchingLastWord(par2ArrayOfStr, NameIDTranslator.getCurseTexts());
        return null;
    }
}
