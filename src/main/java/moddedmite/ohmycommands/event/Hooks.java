package moddedmite.ohmycommands.event;

import moddedmite.ohmycommands.command.building.BuildingHandler;
import net.minecraft.ChatMessageComponent;
import net.minecraft.EntityPlayer;
import net.minecraft.RaycastCollision;

public class Hooks {
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
