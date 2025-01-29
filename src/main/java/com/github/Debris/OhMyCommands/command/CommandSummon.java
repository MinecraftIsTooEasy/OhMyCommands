package com.github.Debris.OhMyCommands.command;

import com.github.Debris.OhMyCommands.api.BlockPos;
import com.github.Debris.OhMyCommands.util.NameIDTranslator;
import net.minecraft.*;

import java.util.List;

import static com.github.Debris.OhMyCommands.util.MiscUtil.getTabCompletionCoordinate;

public class CommandSummon extends CommandBase {

    @Override
    public String getCommandName() {
        return "summon";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.summon.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length < 1)
            throw new WrongUsageException(this.getCommandUsage(iCommandSender));
        double x = (double) iCommandSender.getPlayerCoordinates().posX + 0.5D;
        double y = iCommandSender.getPlayerCoordinates().posY;
        double z = (double) iCommandSender.getPlayerCoordinates().posZ + 0.5D;

        int bound = 30000000;

        if (strings.length >= 4) {
            x = func_110666_a(iCommandSender, x, strings[1], -bound, bound);
            y = func_110666_a(iCommandSender, y, strings[2], -bound, bound);
            z = func_110666_a(iCommandSender, z, strings[3], -bound, bound);
        }

        World world = iCommandSender.getEntityWorld();

        if (!world.blockExists((int) x, (int) y, (int) z)) {
            notifyAdmins(iCommandSender, "commands.world.outOfWorld");
        } else {
            Entity entity = EntityList.createEntityByName(strings[0], world);
            if (entity == null) entity = EntityList.createEntityByID(parseInt(iCommandSender, strings[0]), world);

            if (entity != null) {
                entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
                if (entity instanceof EntityLiving) {
                    ((EntityLiving) entity).onSpawnWithEgg(null);
                }
                world.spawnEntityInWorld(entity);
                notifyAdmins(iCommandSender, "commands.summon.success");
            } else {
                iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("commands.summon.failed"));
            }
        }

    }

    @Override
    public List<?> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] args) {
        if (args.length == 1) return getListOfStringsMatchingLastWord(args, NameIDTranslator.getEntityTexts());
        if (args.length > 1 && args.length <= 4) {
            ChunkCoordinates blockPos = par1ICommandSender.getPlayerCoordinates();
            BlockPos pos = new BlockPos(blockPos.posX, blockPos.posY, blockPos.posZ);
            return getTabCompletionCoordinate(args, 0, pos);
        }
        return null;
    }
}
