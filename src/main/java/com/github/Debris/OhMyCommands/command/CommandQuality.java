package com.github.Debris.OhMyCommands.command;

import net.minecraft.*;

import java.util.Arrays;
import java.util.List;

public class CommandQuality extends CommandBase {

    public String getCommandName() {
        return "quality";
    }

    public String getCommandUsage(ICommandSender ICommandSender) {
        return "commands.quality.usage";
    }


    public int getRequiredPermissionLevel() {
        return 2;
    }

    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, Arrays.stream(EnumQuality.values()).map(EnumQuality::getUnlocalizedName).toArray(String[]::new)) : null;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length < 1) {
            throw new WrongUsageException("commands.quality.usage");
        }
        Arrays.stream(EnumQuality.values())
                .filter(x -> x.getUnlocalizedName().equals(strings[0]))
                .findAny()
                .ifPresentOrElse(x -> {
                    ItemStack heldItemStack = getCommandSenderAsPlayer(iCommandSender).getHeldItemStack();
                    if (heldItemStack == null) {
                        throw new CommandException("commands.fail.emptyHanded");
                    }
                    if (!heldItemStack.getItem().hasQuality()) {
                        throw new CommandException("commands.quality.hasQuality");
                    }
                    heldItemStack.setQuality(x);
                    iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.quality.success", x.getUnlocalizedName()).setColor(EnumChatFormatting.WHITE));
                }, () -> iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("commands.quality.qualityNotFound").setColor(EnumChatFormatting.RED)));

    }
}
