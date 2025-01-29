package com.github.Debris.OhMyCommands.mixins.command.commands;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.CommandBase.joinNiceString;
import static net.minecraft.CommandBase.notifyAdmins;

@Mixin(CommandGameRule.class)
public abstract class CommandGameRuleMixin {
    @Shadow
    protected abstract GameRules getGameRules();

    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender par1ICommandSender, String[] par2ArrayOfStr, CallbackInfo ci) {
        ci.cancel();
        String var6;

        if (par2ArrayOfStr.length == 2) {
            var6 = par2ArrayOfStr[0];
            String var7 = par2ArrayOfStr[1];
            GameRules var8 = this.getGameRules();

            if (var8.hasRule(var6)) {
                var8.setOrCreateGameRule(var6, var7);
                notifyAdmins(par1ICommandSender, "commands.gamerule.success");
            } else {
                notifyAdmins(par1ICommandSender, "commands.gamerule.norule", var6);
            }
        } else if (par2ArrayOfStr.length == 1) {
            var6 = par2ArrayOfStr[0];
            GameRules var4 = this.getGameRules();

            if (var4.hasRule(var6)) {
                String var5 = var4.getGameRuleStringValue(var6);
                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(var6).addText(" = ").addText(var5));
            } else {
                notifyAdmins(par1ICommandSender, "commands.gamerule.norule", var6);
            }
        } else if (par2ArrayOfStr.length == 0) {
            GameRules var3 = this.getGameRules();
            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(joinNiceString(var3.getRules())));
        } else {
            throw new WrongUsageException("commands.gamerule.usage");
        }
    }
}
