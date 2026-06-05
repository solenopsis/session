/*
 * Copyright (C) 2023-2026 Scot P. Floess
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.solenopsis.session.credentials;

import java.util.Objects;
import java.util.Properties;

/**
 *
 * @author sfloess
 */
public enum PropertiesCredentialsEnum {
    URL("url"),
    USERNAME("username"),
    PASSWORD("password"),
    TOKEN("token"),
    VERSION("version"),
    ;

    private final String name;

    private PropertiesCredentialsEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue(final Properties properties, final String defaultValue) {
        return Objects.requireNonNull(properties, "Null properties not allowed!").getProperty(getName(), defaultValue);
    }

    public String getValue(final Properties properties) {
        return Objects.requireNonNull(properties, "Null properties not allowed!").getProperty(getName());
    }
}
