/*
 * This file is part of Charon Configs.
 *
 * Charon Configs is a library that's part of the Charon Framework for Spigot plugins
 * Copyright (C) 2023  SickSkillz
 *
 * Charon Configs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Charon Configs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Charon Configs.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.sickskillz.charon;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CharonConfigs {

    private CharonConfigs() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static @Nullable Plugin plugin = null;

    public static void init(@NotNull Plugin plugin) {
        Objects.requireNonNull(plugin);
        CharonConfigs.plugin = plugin;
    }

    public static @NotNull Plugin getPlugin() {
        if (plugin == null) {
            throw new IllegalStateException("Charon has not been initialized yet! You have to initialize it first by calling Charon.init(plugin)");
        }

        return plugin;
    }
}
