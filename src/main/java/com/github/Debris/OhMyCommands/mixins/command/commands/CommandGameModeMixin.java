package com.github.Debris.OhMyCommands.mixins.command.commands;

import com.github.Debris.OhMyCommands.OhMyCommands;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CommandGameMode.class)
public abstract class CommandGameModeMixin {
    @Shadow
    public abstract int getRequiredPermissionLevel();

    @Shadow
    public abstract String getCommandName();

    @Shadow
    protected abstract EnumGameType getGameModeFromCommand(ICommandSender par1ICommandSender, String par2Str);

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void processCommand(ICommandSender par1ICommandSender, String[] args) {
        if (args.length > 0) {
            ServerPlayer serverPlayer;
            EnumGameType enumGameType = this.getGameModeFromCommand(par1ICommandSender, args[0]);
            serverPlayer = args.length >= 2 ? CommandGameMode.getPlayer(par1ICommandSender, args[1]) : CommandGameMode.getCommandSenderAsPlayer(par1ICommandSender);
            boolean canUse = serverPlayer.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
            if (!(Minecraft.inDevMode() || canUse) && enumGameType != EnumGameType.SURVIVAL) {
                if (OhMyCommands.debugMode) {
                    System.out.println("command game mode: low permission");
                }
                ChatMessageComponent var5 = ChatMessageComponent.createFromTranslationKey("gameMode." + enumGameType.getName());
                if (serverPlayer != par1ICommandSender) {
                    CommandGameMode.notifyAdmins(par1ICommandSender, 1, "commands.gamemode.fail.other", serverPlayer.getEntityName(), var5);
                } else {
                    CommandGameMode.notifyAdmins(par1ICommandSender, 1, "commands.gamemode.fail.self", var5);
                }
                return;
            }
            if (OhMyCommands.debugMode) {
                System.out.println("command game mode: we tried to set type as: " + enumGameType.getName());
            }
            serverPlayer.setGameType(enumGameType);
            serverPlayer.fallDistance = 0.0f;
            ChatMessageComponent var5 = ChatMessageComponent.createFromTranslationKey("gameMode." + enumGameType.getName());
            if (serverPlayer != par1ICommandSender) {
                CommandGameMode.notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.other", serverPlayer.getEntityName(), var5);
            } else {
                CommandGameMode.notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.self", var5);
            }
        } else {
            throw new WrongUsageException("commands.gamemode.usage");
        }
    }
}
