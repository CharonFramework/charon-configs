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

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.sickskillz.charon.CharonConfigs;
import org.sickskillz.charon.exception.ConfigUpdateException;
import org.sickskillz.charon.exception.DefaultConfigFileLoadException;
import org.sickskillz.charon.validator.DefaultValidators;
import org.sickskillz.charon.validator.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    // TODO: getList, getXList
    // TODO: maybe replace isX checks as well?
    // TODO method for getMaterial, getEntity etc. Basically all Spigot validators
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
        Optional<Object> optValue = this.get(path, defaultValidators.getDefaultIntegerValidator());

        if (optValue.isPresent()) {
            Object value = optValue.get();

            if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else if (value instanceof Integer) {
                return (Integer) value;
            }
        }

        return def;
    }

    @Override
    public long getLong(@NotNull String path) {
        Objects.requireNonNull(path);

        return this.getLong(path, 0);
    }

    @Override
    public long getLong(@NotNull String path, long def) {
        Objects.requireNonNull(path);
        Optional<Object> optValue = this.get(path, defaultValidators.getDefaultLongValidator());

        if (optValue.isPresent()) {
            Object value = optValue.get();

            if (value instanceof String) {
                return Long.parseLong((String) value);
            } else if (value instanceof Long) {
                return (Long) value;
            }
        }

        return def;
    }

    @Override
    public double getDouble(@NotNull String path) {
        Objects.requireNonNull(path);

        return this.getDouble(path, 0);
    }

    @Override
    public double getDouble(@NotNull String path, double def) {
        Objects.requireNonNull(path);
        Optional<Object> optValue = this.get(path, defaultValidators.getDefaultDoubleValidator());

        if (optValue.isPresent()) {
            Object value = optValue.get();

            if (value instanceof Double) {
                return (Double) value;
            } else if (value instanceof Float || value instanceof Integer) {
                return ((Number) value).doubleValue();
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            }
        }

        return def;
    }

    @Override
    public boolean getBoolean(@NotNull String path) {
        Objects.requireNonNull(path);

        return this.getBoolean(path, false);
    }

    @Override
    public boolean getBoolean(@NotNull String path, boolean def) {
        Objects.requireNonNull(path);
        Optional<Object> optValue = this.get(path, defaultValidators.getDefaultBooleanValidator());

        if (optValue.isPresent()) {
            Object value = optValue.get();

            if (value instanceof Boolean) {
                return (Boolean) value;
            } else if (value instanceof String) {
                return Boolean.parseBoolean((String) value);
            }
        }

        return def;
    }

    @Override
    public String getString(@NotNull String path) {
        Objects.requireNonNull(path);

        return this.getString(path, null);
    }

    @Override
    public String getString(@NotNull String path, String def) {
        Objects.requireNonNull(path);
        Optional<Object> optValue = this.get(path, defaultValidators.getDefaultStringValidator());

        if (optValue.isPresent()) {
            Object value = optValue.get();

            if (value instanceof String) {
                return (String) value;
            } else {
                return value.toString();
            }
        }

        return def;
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
