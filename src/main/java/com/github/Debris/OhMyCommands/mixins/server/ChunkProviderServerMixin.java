package com.github.Debris.OhMyCommands.mixins.server;

import com.github.Debris.OhMyCommands.api.OMCChunkProviderServer;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.ChunkProviderServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashSet;
import java.util.Set;

@Mixin(ChunkProviderServer.class)
public abstract class ChunkProviderServerMixin implements OMCChunkProviderServer {
    @Unique
    private Set<Long> forceLoadChunks = new HashSet<>();

    @WrapWithCondition(method = "unloadChunksIfNotNearSpawn", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
    private <E> boolean prevent(Set<?> instance, E e) {
        return !this.omc$IsChunkForceLoaded((Long) e);
    }

    @Override
    public void omc$AddChunk(long chunk) {
        this.forceLoadChunks.add(chunk);
    }

    @Override
    public void omc$RemoveChunk(long chunk) {
        this.forceLoadChunks.remove(chunk);
    }

    @Override
    public void omc$ClearChunks() {
        this.forceLoadChunks.clear();
    }

    @Override
    public Set<Long> omc$GetChunks() {
        return this.forceLoadChunks;
    }

    @Override
    public boolean omc$IsChunkForceLoaded(long chunk) {
        return this.forceLoadChunks.contains(chunk);
    }
}
