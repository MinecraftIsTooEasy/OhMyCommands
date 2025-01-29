package com.github.Debris.OhMyCommands.command;

import net.minecraft.*;

import java.util.Arrays;
import java.util.List;

public class CommandDimension extends CommandBase {

    public String getCommandName() {
        return "dimension";
    }

    public String getCommandUsage(ICommandSender ICommandSender) {
        return "commands.dimension.usage";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, Arrays.stream(EnumDimension.values()).map(x -> x.en_name).toArray(String[]::new)) : null;
    }

    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length != 1) {
            throw new WrongUsageException("commands.dimension.usage");
        }
        Arrays.stream(EnumDimension.values()).filter(x -> x.en_name.equals(strings[0])).findAny().ifPresentOrElse(x -> {
            if (iCommandSender.getEntityWorld().provider.dimensionId != x.id) {
                ServerPlayer player = getCommandSenderAsPlayer(iCommandSender);
                player.timeUntilPortal = 100;
                player.travelToDimension(x.id);
                iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.dimension.success", x.getUnlocalizedName()).setColor(EnumChatFormatting.WHITE));
            } else {
                iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.dimension.duplicateDestination", x.getUnlocalizedName()).setColor(EnumChatFormatting.RED));
            }
        }, () -> iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("commands.dimension.dimensionNotFound").setColor(EnumChatFormatting.RED)));
    }

    private enum EnumDimension {
        OverWorld(0, "overWorld", "主世界"),
        Hell(-1, "hell", "地狱"),
        UnderWorld(-2, "underWorld", "地下世界"),
        End(1, "end", "末地"),
        ;
        final int id;
        final String en_name;
        final String zh_name;

        EnumDimension(int id, String en_name, String zh_name) {
            this.id = id;
            this.en_name = en_name;
            this.zh_name = zh_name;
        }

        public String getUnlocalizedName() {
            return "commands.dimension." + this.en_name;
        }
    }
}
