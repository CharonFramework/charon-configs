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

package org.sickskillz.charon.validators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.sickskillz.charon.utils.ParseUtils;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DoubleValidator extends Validator {

    @Builder.Default
    private double minimumValue = Double.MIN_VALUE;

    @Builder.Default
    private double maximumValue = Double.MAX_VALUE;

    @Override
    protected boolean isValueValid(@NotNull Object value) {
        Objects.requireNonNull(value);

        if (value instanceof Double) {
            return isDoubleValid((double) value);
        } else if (value instanceof String) {
            return isStringValidDouble((String) value);
        }

        return false;
    }

    private boolean isStringValidDouble(@NotNull String value) {
        Objects.requireNonNull(value);

        return ParseUtils.parseDoubleSafe(value)
                .map(this::isDoubleValid)
                .orElse(false);
    }

    private boolean isDoubleValid(double value) {
        return value >= minimumValue && value <= maximumValue;
    }
}
