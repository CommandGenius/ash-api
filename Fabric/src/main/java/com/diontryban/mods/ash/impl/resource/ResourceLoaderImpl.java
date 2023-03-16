/*
 * This file is part of Ash API.
 * A copy of this program can be found at https://github.com/Trikzon/ash-api.
 * Copyright (C) 2023 Dion Tryban
 *
 * Ash API is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * Ash API is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Ash API. If not, see <https://www.gnu.org/licenses/>.
 */

package com.diontryban.mods.ash.impl.resource;

import com.diontryban.mods.ash.api.resource.ResourceLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ResourceLoaderImpl extends ResourceLoader {
    private final ResourceManagerHelper resourceManagerHelper;

    /**
     * Empty constructor only to be used by ImplementationLoader.
     *
     * @deprecated Use {@link ResourceLoader#get(PackType)}
     */
    public ResourceLoaderImpl() {
        this.resourceManagerHelper = null;
    }

    private ResourceLoaderImpl(PackType type) {
        this.resourceManagerHelper = ResourceManagerHelper.get(type);
    }

    @Override
    public void registerReloadListenerImpl(PreparableReloadListener reloadListener) {
        resourceManagerHelper.registerReloadListener(
                new IdentifiableResourceReloadListener() {
                    @Override
                    public ResourceLocation getFabricId() {
                        return new ResourceLocation(reloadListener.getName());
                    }

                    @Override
                    public CompletableFuture<Void> reload(
                            PreparationBarrier preparationBarrier,
                            ResourceManager resourceManager,
                            ProfilerFiller preparationsProfiler,
                            ProfilerFiller reloadProfiler,
                            Executor backgroundExecutor,
                            Executor gameExecutor
                    ){
                        return reloadListener.reload(
                                preparationBarrier,
                                resourceManager,
                                preparationsProfiler,
                                reloadProfiler,
                                backgroundExecutor,
                                gameExecutor
                        );
                    }
                }
        );
    }

    @Override
    public ResourceLoader getImpl(PackType type) {
        return new ResourceLoaderImpl(type);
    }
}
