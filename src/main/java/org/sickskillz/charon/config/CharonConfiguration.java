/*
 * This file is part of Charon.
 *
 * Charon is a library that extends the SpigotMC YAML Configuration API
 * Copyright (C) 2023  SickSkillz
 *
 * Charon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Charon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Charon.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.sickskillz.charon.config;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.sickskillz.charon.CharonConfigs;
import org.sickskillz.charon.exceptions.ConfigUpdateException;
import org.sickskillz.charon.exceptions.DefaultConfigFileLoadException;
import org.sickskillz.charon.validators.DefaultValidators;
import org.sickskillz.charon.validators.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Getter
@Setter
public class CharonConfiguration extends YamlConfiguration {

    private @NotNull List<String> verificationExclusion = new ArrayList<>();
    private final @NotNull YamlConfiguration defaultConfig;

    private final @NotNull File configFile;

    private @NotNull DefaultValidators defaultValidators = new DefaultValidators();

    public CharonConfiguration(@NotNull String fileName) {
        Objects.requireNonNull(fileName, "fileName cannot be null");

        try (InputStream resourceStream = CharonConfiguration.class.getResourceAsStream("/" + fileName)) {
            if (resourceStream == null) {
                throw new DefaultConfigFileLoadException("Unable to load the default config from your jar file using filename "
                        + fileName + ". Make sure that the file exists in your jar file by having it at the root of your resources folder!");
            }

            try (InputStreamReader reader = new InputStreamReader(resourceStream, StandardCharsets.UTF_8)) {
                this.defaultConfig = YamlConfiguration.loadConfiguration(reader);
            }
        } catch (IOException e) {
            throw new DefaultConfigFileLoadException("Unable to load the default config: " + e);
        }

        configFile = new File(CharonConfigs.getPlugin().getDataFolder(), fileName);

        if (!configFile.exists()) {
            CharonConfigs.getPlugin().saveResource(fileName, false);
        }
    }

    public void addVerificationExclusion(@NotNull String verificationExclusion) {
        Objects.requireNonNull(verificationExclusion);
        this.verificationExclusion.add(verificationExclusion);
    }

    public void removeVerificationExclusion(@NotNull String verificationExclusion) {
        Objects.requireNonNull(verificationExclusion);
        this.verificationExclusion.remove(verificationExclusion);
    }

    public boolean isExcludedFromVerification(@NotNull String key) {
        Objects.requireNonNull(key);

        for (String exclusion : verificationExclusion) {
            if (key.startsWith(exclusion)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object get(@NotNull String path) {
        Objects.requireNonNull(path);
        Object object = super.get(path);

        if (object == null) {
            object = this.defaultConfig.get(path);

            if (object != null) {
                this.set(path, object);

                try {
                    this.save(configFile);
                } catch (IOException e) {
                    throw new ConfigUpdateException("Unable to save the config for an unknown reason! " + e);
                }
            } else {
                throw new NullPointerException("Unable to find the key " + path + " in the default config file, while it is also missing in the config file on the disk!");
            }
        }

        return object;
    }

    public @NotNull Optional<Object> get(@NotNull String path, @NotNull Validator validator) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(validator);

        Object value = this.get(path);

        if (value != null && validator.isValid(path, value)) {
            return Optional.of(value);
        }

        return Optional.empty();
    }

    @Override
    public int getInt(@NotNull String path) {
        Objects.requireNonNull(path);

        return this.getInt(path, 0);
    }

    @Override
    public int getInt(@NotNull String path, int def) {
        Objects.requireNonNull(path);
        Optional<Object> value = this.get(path, defaultValidators.getDefaultIntegerValidator());

        return value.map(o -> (int) o).orElse(def);

    }

    public @NotNull List<String> updateConfig() {
        List<String> updatedKeys = new ArrayList<>();

        for (String key : this.defaultConfig.getConfigurationSection("").getKeys(true)) {
            if (this.isExcludedFromVerification(key)) {
                continue;
            }

            if (super.get(key) == null) {
                Object defaultValue = this.defaultConfig.get(key);

                this.set(key, defaultValue);
                updatedKeys.add(key);
            }
        }

        try {
            this.save(configFile);
        } catch (IOException e) {
            throw new ConfigUpdateException("Unable to save the config for an unknown reason! " + e);
        }

        return updatedKeys;
    }
}
