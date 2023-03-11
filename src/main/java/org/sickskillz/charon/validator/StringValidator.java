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

package org.sickskillz.charon.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StringValidator extends Validator {

    @Builder.Default
    private int minLength = Integer.MIN_VALUE;

    @Builder.Default
    private int maxLength = Integer.MAX_VALUE;

    private Pattern regex;

    private List<Character> allowedCharacters;

    private List<Character> disallowedCharacters;

    private List<String> allowedValues;

    private List<String> disallowedValues;

    @Builder.Default
    private boolean caseInsensitive = false;

    @Override
    protected boolean isValueValid(@NotNull Object value) {
        Objects.requireNonNull(value);

        String stringValue;

        if (value instanceof String) {
            stringValue = (String) value;
        } else {
            stringValue = value.toString();
        }

        if (isStringLengthInvalid(stringValue)) return false;

        if (containsInvalidCharacters(stringValue)) return false;

        if (isRegexInvalid(stringValue)) return false;

        if (doesNotContainAllowedValue(stringValue)) return false;

        return !containsDisallowedValue(stringValue);
    }

    private boolean containsDisallowedValue(String stringValue) {
        if (disallowedValues != null && !disallowedValues.isEmpty()) {
            if (caseInsensitive) {
                return disallowedValues.contains(stringValue);
            } else {
                return disallowedValues.stream().anyMatch(stringValue::equalsIgnoreCase);
            }
        }
        return false;
    }

    private boolean doesNotContainAllowedValue(String stringValue) {
        if (allowedValues != null && !allowedValues.isEmpty()) {
            if (caseInsensitive) {
                return !allowedValues.contains(stringValue);
            } else {
                return allowedValues.stream().noneMatch(stringValue::equalsIgnoreCase);
            }
        }
        return false;
    }

    private boolean isRegexInvalid(String stringValue) {
        return regex != null && !regex.matcher(stringValue).matches();
    }

    private boolean containsInvalidCharacters(String stringValue) {
        for (char character : stringValue.toCharArray()) {
            if (caseInsensitive) {
                character = Character.toLowerCase(character);
            }

            if (allowedCharacters != null && !allowedCharacters.contains(character)) {
                return true;
            }

            if (disallowedCharacters != null && disallowedCharacters.contains(character)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStringLengthInvalid(String stringValue) {
        return stringValue.length() < minLength || stringValue.length() > maxLength;
    }
}
