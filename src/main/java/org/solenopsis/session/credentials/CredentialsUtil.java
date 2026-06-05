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

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import org.flossware.jcommons.util.PropertyUtil;
import org.solenopsis.session.Credentials;

/**
 *
 * @author sfloess
 */
public final class CredentialsUtil {
        /**
     * Our logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CredentialsUtil.class.getName());

    /**
     * Return the logger.
     *
     * @return our logger.
     */
    static Logger getLogger() {
        return LOGGER;
    }

    private CredentialsUtil() {
    }

    public static Credentials fromValues(final String url, final String username, final String password, final String token, final String version) {
        return new CredentialsRecord(url, username, password, token, version);
    }

    public static Credentials fromProperties(final Properties properties) {
        Objects.requireNonNull(properties, "Null properties not allowed!");

        return fromValues(
            PropertiesCredentialsEnum.URL.getValue(properties),
            PropertiesCredentialsEnum.USERNAME.getValue(properties),
            PropertiesCredentialsEnum.PASSWORD.getValue(properties),
            PropertiesCredentialsEnum.TOKEN.getValue(properties),
            PropertiesCredentialsEnum.VERSION.getValue(properties)
        );
    }

    public static Credentials fromInputStream(final InputStream inputStream, final boolean closeStream) {
        return fromProperties(PropertyUtil.fromInputStream(inputStream, closeStream));
    }

    public static Credentials fromInputStream(final InputStream inputStream) {
        return fromProperties(PropertyUtil.fromInputStream(inputStream));
    }

    public static Credentials fromReader(final Reader reader, final boolean closeReader) {
        return fromProperties(PropertyUtil.fromReader(reader, closeReader));
    }

    public static Credentials fromReader(final Reader reader) {
        return fromProperties(PropertyUtil.fromReader(reader));
    }

    public static Credentials fromResource(final String resource) {
        return fromProperties(PropertyUtil.fromResource(resource));
    }

    public static Credentials fromString(final String string) {
        return fromReader(new StringReader(string), true);
    }

    public static Credentials fromFile(final File file) {
        return fromProperties(PropertyUtil.fromFile(file));
    }

    public static Credentials fromFile(final String file) {
        return fromFile(new File(file));
    }
}