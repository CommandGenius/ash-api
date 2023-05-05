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

package com.diontryban.ash.impl.client.gui.screens;

import com.diontryban.ash.api.client.gui.screens.ModOptionsScreenFactory;
import com.diontryban.ash.api.client.gui.screens.ModOptionsScreenRegistry;
import com.diontryban.ash.api.options.ModOptions;
import com.diontryban.ash.api.options.ModOptionsManager;
import net.minecraft.client.gui.screens.Screen;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModOptionsScreenRegistryImpl extends ModOptionsScreenRegistry {
    public static final Map<String, Function<Screen, ? extends Screen>> MOD_OPTIONS_SCREENS = new HashMap<>();

    @Override
    protected <S extends Screen, O extends ModOptions> void registerModOptionsScreenImpl(
            ModOptionsManager<O> options,
            ModOptionsScreenFactory<S, O> factory
    ) {
        MOD_OPTIONS_SCREENS.put(options.getModId(), parent -> factory.create(options, parent));
    }
}
