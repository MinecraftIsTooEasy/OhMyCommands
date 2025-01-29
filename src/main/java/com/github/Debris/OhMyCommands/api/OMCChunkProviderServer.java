package com.github.Debris.OhMyCommands.api;

import java.util.Set;

public interface OMCChunkProviderServer {

    void omc$AddChunk(long chunk);

    void omc$RemoveChunk(long chunk);

    void omc$ClearChunks();

    Set<Long> omc$GetChunks();

    boolean omc$IsChunkForceLoaded(long chunk);
}
