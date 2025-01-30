package com.github.Debris.OhMyCommands.mixins.command.commands;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(CommandWeather.class)
public abstract class CommandWeatherMixin extends CommandBase {

    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void injectWeather(ICommandSender par1ICommandSender, String[] par2ArrayOfStr, CallbackInfo ci) {
        ci.cancel();
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr.length <= 2) {
            int defaultTime = (300 + (new Random()).nextInt(600)) * 20;

            if (par2ArrayOfStr.length >= 2) {
                defaultTime = parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 1, 1000000) * 20;
            }

            WorldServer worldServer = MinecraftServer.getServer().worldServers[0];
            World world = Minecraft.getMinecraft().theWorld;
            List weather = world.getWeatherEventsForToday();
            long nowTime = worldServer.getTotalWorldTime() - 1;

            if ("clear".equalsIgnoreCase(par2ArrayOfStr[0])) {
                for (int i = 0; i < weather.size(); i++) {
                    ((WeatherEvent) weather.get(i)).setStartAndEnd(nowTime, nowTime);
                    weather.remove(i);
                }
                notifyAdmins(par1ICommandSender, "commands.weather.clear");
            } else if ("rain".equalsIgnoreCase(par2ArrayOfStr[0])) {
                WeatherEvent rain = new WeatherEvent(worldServer.getTotalWorldTime(), defaultTime);
                rain.setStartAndEnd(worldServer.getTotalWorldTime(), worldServer.getTotalWorldTime() + defaultTime);
                world.getWeatherEventsForToday().add(rain);
                notifyAdmins(par1ICommandSender, "commands.weather.rain");
            } else {
                if (!"thunder".equalsIgnoreCase(par2ArrayOfStr[0])) {
                    throw new WrongUsageException("commands.weather.usage");
                }
                WeatherEvent thunder = new WeatherEvent(worldServer.getTotalWorldTime(), defaultTime);
                thunder.setStorm(worldServer.getTotalWorldTime(), worldServer.getTotalWorldTime() + defaultTime);
                world.getWeatherEventsForToday().add(thunder);
                notifyAdmins(par1ICommandSender, "commands.weather.thunder");
            }
        } else {
            throw new WrongUsageException("commands.weather.usage");
        }
    }
}
