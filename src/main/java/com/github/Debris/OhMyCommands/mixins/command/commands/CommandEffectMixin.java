package com.github.Debris.OhMyCommands.mixins.command.commands;

import com.github.Debris.OhMyCommands.util.NameIDTranslator;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.CommandBase.*;

@Mixin(CommandEffect.class)
public abstract class CommandEffectMixin {
    @Shadow
    protected abstract String[] getAllUsernames();

    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender iCommandSender, String[] args, CallbackInfo ci) {
        ci.cancel();
        if (args.length < 2) {
            throw new WrongUsageException("commands.effect.usage");
        }
        ServerPlayer player = getPlayer(iCommandSender, args[0]);
        if (args[1].equals("clear")) {
            if (player.getActivePotionEffects().isEmpty()) {
                throw new CommandException("commands.effect.failure.notActive.all", player.getEntityName());
            }
            player.clearActivePotions();
            notifyAdmins(iCommandSender, "commands.effect.success.removed.all", player.getEntityName());
            return;
        }
        if (!args[1].equals("add"))
            throw new WrongUsageException("commands.effect.usage");

        Potion potion = NameIDTranslator.getEffectIdByText(iCommandSender, args[2]);
        if (potion == null)
            throw new NumberInvalidException("commands.effect.notFound", args[2]);

        int durationTick = 600;
        int durationSecond = 30;
        int level = 0;
        if (args.length >= 4) {
            durationSecond = parseIntBounded(iCommandSender, args[3], 0, 1000000);
            if (potion.isInstant()) {
                durationTick = durationSecond;
            } else {
                durationTick = durationSecond * 20;
            }
        } else if (potion.isInstant()) {
            durationTick = 1;
        }
        if (args.length >= 5) {
            level = parseIntBounded(iCommandSender, args[4], 0, 255);
        }
        if (durationSecond == 0) {
            if (!player.isPotionActive(potion)) {
                throw new CommandException("commands.effect.failure.notActive", ChatMessageComponent.createFromTranslationKey(potion.getName()), player.getEntityName());
            }
            player.removePotionEffect(potion.id);
            notifyAdmins(iCommandSender, "commands.effect.success.removed", ChatMessageComponent.createFromTranslationKey(potion.getName()), player.getEntityName());
        } else {
            PotionEffect finalPotion = new PotionEffect(potion.id, durationTick, level);
            player.addPotionEffect(finalPotion);
            notifyAdmins(iCommandSender, "commands.effect.success", ChatMessageComponent.createFromTranslationKey(finalPotion.getEffectName()), potion.id, level, player.getEntityName(), durationSecond);
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1)
            return CommandEffect.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllUsernames());
        if (par2ArrayOfStr.length == 2) return getListOfStringsMatchingLastWord(par2ArrayOfStr, "add", "clear");
        if (par2ArrayOfStr.length == 3 && par2ArrayOfStr[1].equals("add"))
            return getListOfStringsMatchingLastWord(par2ArrayOfStr, NameIDTranslator.getEffectTexts());
        return null;
    }
}
