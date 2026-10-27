package moddedmite.ohmycommands.mixins.command;

import moddedmite.ohmycommands.event.Hooks;
import net.minecraft.CommandHandler;
import net.minecraft.ServerCommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommandManager.class)
public class ServerCommandManagerMixin extends CommandHandler {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectInit(CallbackInfo callbackInfo) {
        Hooks.registerCommands(this::registerCommand);
    }
}
