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

package org.sickskillz.charon.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private final HashMap<String, CharonConfiguration> configs = new HashMap<>();

    public CharonConfiguration getConfig(String name) {
        if (configs.containsKey(name)) {
            return configs.get(name);
        } else {
            CharonConfiguration config = new CharonConfiguration(name);
            configs.put(name, config);
            return config;
        }
    }

    public void saveAll() {
        configs.forEach((name, config) -> config.save());
    }

    public void reloadAll() {
        List<String> names = new ArrayList<>(configs.keySet());

        configs.clear();

        names.forEach(name -> configs.put(name, new CharonConfiguration(name)));
    }

    public void clearAll() {
        configs.clear();
    }
}
