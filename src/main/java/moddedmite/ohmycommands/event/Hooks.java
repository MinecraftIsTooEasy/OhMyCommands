package moddedmite.ohmycommands.event;

import moddedmite.ohmycommands.command.*;
import moddedmite.ohmycommands.command.building.*;
import net.minecraft.ChatMessageComponent;
import net.minecraft.EntityPlayer;
import net.minecraft.ICommand;
import net.minecraft.RaycastCollision;

import java.util.function.Consumer;

public class Hooks {
    public static void registerCommands(Consumer<ICommand> consumer) {
        consumer.accept(new CommandSummon());
        consumer.accept(new CommandLocate());
        consumer.accept(new CommandLocateBiome());
        consumer.accept(new CommandCurse());
        consumer.accept(new CommandDimension());
        consumer.accept(new CommandFill());
        consumer.accept(new CommandSetBlock());
        consumer.accept(new CommandPos1());
        consumer.accept(new CommandPos2());
        consumer.accept(new CommandSet());
        consumer.accept(new CommandHomo());
        consumer.accept(new CommandStack());
        consumer.accept(new CommandForceLoad());
        consumer.accept(new CommandQuality());
        consumer.accept(new CommandDamage());
        consumer.accept(new CommandSetWorldSpawn());
        consumer.accept(new CommandDay());
    }

    public static boolean useFlintAxe(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        if (player.rightClickCancelled()) return false;
        player.getPlayerController().setUseButtonDelay();
        RaycastCollision rc = player.getSelectedObject(partial_tick, false);
        if (rc == null) {
            return false;
        }
        if (rc.isBlock()) {
            int x = rc.block_hit_x;
            int y = rc.block_hit_y;
            int z = rc.block_hit_z;
            BuildingHandler.getInstance().setPos2(x, y, z);
            player.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.pos.pos2Set", x, y, z));
        }
        return true;
    }
}
