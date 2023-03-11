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

package org.sickskillz.charon.validator.spigot;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.sickskillz.charon.validator.Validator;

import java.util.Objects;

@NoArgsConstructor
@SuperBuilder
public class MaterialValidator extends Validator {

    @Override
    protected boolean isValueValid(@NotNull Object value) {
        Objects.requireNonNull(value);

        if (!(value instanceof String)) {
            return false;
        }

        try {
            Material.valueOf((String) value);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
