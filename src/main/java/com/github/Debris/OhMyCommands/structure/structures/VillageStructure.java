package com.github.Debris.OhMyCommands.structure.structures;

import com.github.Debris.OhMyCommands.structure.RegionStructure;
import net.minecraft.*;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class VillageStructure extends RegionStructure {
    public VillageStructure(String key, int spacing, int separation, int salt, List<BiomeGenBase> validBiomes) {
        super(key, spacing, separation, salt, validBiomes);
    }

    @Override
    public boolean checkCondition(ICommandSender iCommandSender) {
        WorldInfo world_info = iCommandSender.getEntityWorld().getWorldInfo();
        if (world_info.getVillageConditions() < WorldInfo.getVillagePrerequisites()) {
            iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("commands.locate.village.requirement").setColor(EnumChatFormatting.YELLOW));
            if (!BitHelper.isBitSet(world_info.getVillageConditions(), 16)) {
                iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("commands.locate.village.ironPickaxe").setColor(EnumChatFormatting.YELLOW));
            }
            return false;
        } else if (MinecraftServer.getServer().worldServers[0].getDayOfWorld() < 60) {
            iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("commands.locate.village.day").setColor(EnumChatFormatting.YELLOW));
            return false;
        }
        return true;
    }

}
