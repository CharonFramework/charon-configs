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

package org.sickskillz.charon.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class ParseUtils {

    private ParseUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Optional<Integer> parseIntSafe(@NotNull String value) {
        Objects.requireNonNull(value);

        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Double> parseDoubleSafe(@NotNull String value) {
        Objects.requireNonNull(value);

        try {
            return Optional.of(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Long> parseLongSafe(@NotNull String value) {
        Objects.requireNonNull(value);

        try {
            return Optional.of(Long.parseLong(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Boolean> parseBooleanSafe(@NotNull String value) {
        Objects.requireNonNull(value);

        try {
            return Optional.of(Boolean.parseBoolean(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
