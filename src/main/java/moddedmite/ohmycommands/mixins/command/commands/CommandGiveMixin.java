package moddedmite.ohmycommands.mixins.command.commands;

import moddedmite.ohmycommands.util.JsonToNBT;
import moddedmite.ohmycommands.util.NBTException;
import moddedmite.ohmycommands.util.NameIDTranslator;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mixin(CommandGive.class)
public abstract class CommandGiveMixin extends CommandBase {
    @Shadow protected abstract String[] getPlayers();

    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender sender, String[] args, CallbackInfo ci) {
        ci.cancel();
        if (args.length < 2) {
            throw new WrongUsageException("commands.give.usage", new Object[0]);
        } else {
            ServerPlayer player = getPlayer(sender, args[0]);
            Item item = NameIDTranslator.getItemByText(sender, args[1]);
            int num = 1;
            int subtype = 0;
            if (args.length >= 3) {
                num = parseIntBounded(sender, args[2], 1, 64);
            }
            if (args.length >= 4) {
                subtype = parseInt(sender, args[3]);
            }
            ItemStack stack = new ItemStack(item, num, subtype);
            if (args.length >= 5) {
                String s = func_82360_a(sender, args, 4);
                try {
                    NBTBase nbtbase = JsonToNBT.getTagFromJson(s);
                    if (!(nbtbase instanceof NBTTagCompound)) {
                        notifyAdmins(sender, "commands.give.tagError", new Object[]{"Not a valid tag"});
                        return;
                    }
                    stack.setTagCompound((NBTTagCompound) nbtbase);
                } catch (NBTException nbtexception) {
                    notifyAdmins(sender, "commands.give.tagError", new Object[]{nbtexception.getMessage()});
                    return;
                }
            }
            EntityItem entityitem = player.dropPlayerItemWithRandomChoice(stack, false);
            entityitem.delayBeforeCanPickup = 0;
            notifyAdmins(sender, "commands.give.success", func_151000_E(stack), num, player.getCommandSenderName());
        }
    }

    @Inject(method = "addTabCompletionOptions", at = @At("HEAD"), cancellable = true)
    public void inject(ICommandSender sender, String[] args, CallbackInfoReturnable<List> cir) {
        if (args.length == 1) {
            cir.setReturnValue(getListOfStringsMatchingLastWord(args, this.getPlayers()));
        } else {
            cir.setReturnValue(args.length == 2 ? getListOfStringsFromIterableMatchingLastWord(args, Arrays.asList(NameIDTranslator.getStackTexts())) : Collections.emptyList());
        }
    }
    
    
    public ChatMessageComponent func_151000_E(ItemStack stack) {
        ChatMessageComponent component = (ChatMessageComponent.createFromText("[")).addText(stack.getDisplayName()).addText("]");
        if (stack.getItem() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            stack.writeToNBT(nbttagcompound);
            component.setColor(EnumChatFormatting.getByChar((char) stack.getRarity().rarityColor));
        }
        return component;
    }
}
