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

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharonConfiguration extends YamlConfiguration {

    private List<String> verificationExclusion = new ArrayList<>();
    private YamlConfiguration defaultConfig;

    public CharonConfiguration(String fileName) {
        Objects.requireNonNull(fileName, "fileName cannot be null");
        try (InputStream resourceStream = CharonConfiguration.class.getResourceAsStream("/" + fileName)) {
            Objects.requireNonNull(resourceStream, "Unable to load the default config from your jar file using filename "
                    + fileName + ". Make sure that the file exists in your jar file by having it at the root of your resources folder!");

            try (InputStreamReader reader = new InputStreamReader(resourceStream, StandardCharsets.UTF_8)) {
                this.defaultConfig = YamlConfiguration.loadConfiguration(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Objects.requireNonNull(this.defaultConfig, "Unable to load the default config for an unknown reason!");
    }

    public List<String> getVerificationExclusion() {
        return verificationExclusion;
    }

    public void setVerificationExclusion(List<String> verificationExclusion) {
        this.verificationExclusion = verificationExclusion;
    }

    public void addVerificationExclusion(String verificationExclusion) {
        this.verificationExclusion.add(verificationExclusion);
    }

    public void removeVerificationExclusion(String verificationExclusion) {
        this.verificationExclusion.remove(verificationExclusion);
    }
}
