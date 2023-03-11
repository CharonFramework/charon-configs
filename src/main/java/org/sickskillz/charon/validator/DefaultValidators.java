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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultValidators {

    private Validator defaultIntegerValidator = new IntegerValidator();

    private Validator defaultDoubleValidator = new DoubleValidator();

    private Validator defaultLongValidator = new LongValidator();

    private Validator defaultBooleanValidator = new BooleanValidator();

    private Validator defaultStringValidator = new StringValidator();

}
