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
import org.jetbrains.annotations.Nullable;
import org.sickskillz.charon.utils.TypeUtils;

import java.util.Objects;
import java.util.function.BiFunction;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class IntegerValidator extends Validator {

    @Builder.Default
    private int minimumValue = Integer.MIN_VALUE;

    @Builder.Default
    private int maximumValue = Integer.MAX_VALUE;

    @Override
    protected boolean isValueValid(@NotNull Object value) {
        Objects.requireNonNull(value);

        if (value instanceof Integer) {
            return isIntegerValid((int) value);
        } else if (value instanceof String) {
            return isStringValidInteger((String) value);
        }

        return false;
    }

    private boolean isStringValidInteger(@NotNull String value) {
        Objects.requireNonNull(value);

        if (!TypeUtils.isInteger(value)) {
            return false;
        }

        int parsedValue = Integer.parseInt(value);
        return isIntegerValid(parsedValue);
    }

    private boolean isIntegerValid(int value) {
        return value >= minimumValue && value <= maximumValue;
    }
}
