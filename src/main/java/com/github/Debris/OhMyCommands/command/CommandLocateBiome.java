package com.github.Debris.OhMyCommands.command;

import com.github.Debris.OhMyCommands.api.BiomeAccessor;
import com.github.Debris.OhMyCommands.util.MiscUtil;
import com.github.Debris.OhMyCommands.util.NameIDTranslator;
import net.minecraft.*;

import java.util.List;

public class CommandLocateBiome extends CommandBase {
    @Override
    public String getCommandName() {
        return "locateBiome";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.locateBiome.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length != 1) {
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        }
        int radius = 4096;
        ChunkCoordinates playerPosition = iCommandSender.getPlayerCoordinates();
        String info = Translator.getFormatted("commands.locateBiome.notFound", strings[0], radius);

        BiomeGenBase biome = NameIDTranslator.getBiomeByText(iCommandSender, strings[0]);

        if (biome == null) {
            throw new CommandException("commands.locateBiome.wrongBiomeName");
        }

        ChunkPosition chunkPosition = ((BiomeAccessor) iCommandSender.getEntityWorld().getWorldChunkManager()).omc$locateBiome
                (biome, playerPosition.posX, playerPosition.posZ, radius);
        if (chunkPosition != null) {
            int x = chunkPosition.x;
            int z = chunkPosition.z;
            info = Translator.getFormatted("commands.locateBiome.found", strings[0], x, z);
            MiscUtil.copyToClipBoard("/tp " + x + " " + 100 + " " + z);
        }
        iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(info));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, NameIDTranslator.getBiomeTexts()) : null;
    }
}
