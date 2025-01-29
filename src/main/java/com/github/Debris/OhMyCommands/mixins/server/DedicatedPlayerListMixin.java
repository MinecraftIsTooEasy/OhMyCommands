package com.github.Debris.OhMyCommands.mixins.server;

import net.minecraft.DedicatedPlayerList;
import net.minecraft.DedicatedServer;
import net.minecraft.ServerConfigurationManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.*;
import java.util.Iterator;

@Mixin(DedicatedPlayerList.class)
public abstract class DedicatedPlayerListMixin extends ServerConfigurationManager {

    @Shadow
    private File opsList;

    @Shadow
    public abstract DedicatedServer getDedicatedServerInstance();

    public DedicatedPlayerListMixin(MinecraftServer par1MinecraftServer) {
        super(par1MinecraftServer);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void loadOpsList() {
        try {
            this.getOps().clear();
            BufferedReader var1 = new BufferedReader(new FileReader(this.opsList));
            String var2;
            while ((var2 = var1.readLine()) != null) {
                this.getOps().add(var2.trim().toLowerCase());
            }
            var1.close();
        } catch (Exception var3) {
            this.getDedicatedServerInstance().getLogAgent().logWarning("Failed to load operators list: " + var3);
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void saveOpsList() {
        try {
            PrintWriter var1 = new PrintWriter(new FileWriter(this.opsList, false));
            Iterator var2 = this.getOps().iterator();

            while (var2.hasNext()) {
                String var3 = (String) var2.next();
                var1.println(var3);
            }

            var1.close();
        } catch (Exception var4) {
            this.getDedicatedServerInstance().getLogAgent().logWarning("Failed to save operators list: " + var4);
        }
    }
}
