package moddedmite.ohmycommands.command;

import moddedmite.ohmycommands.util.Components;
import net.minecraft.EnumChatFormatting;
import net.minecraft.ICommandSender;
import net.minecraft.WorldServer;
import net.minecraft.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandDay extends AbstractCommand {
    public CommandDay() {
        super("day");
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        int length = strings.length;
        if (length == 0) {
            iCommandSender.sendChatToPlayer(
                    Components.literal("It is day " +
                            MinecraftServer.getServer().worldServers[0].getDayOfWorld() +
                            " of this world"
                    ).setColor(EnumChatFormatting.YELLOW)
            );
            return;
        }
        if (length == 1) {
            WorldServer world = (WorldServer) iCommandSender.getEntityWorld();
            int new_total_world_time = (Integer.parseInt(strings[0]) - 1) * 24000;
            world.setTotalWorldTime(new_total_world_time, true);
            return;
        }
        throw new WrongUsageException(this.getCommandName());
    }
}
