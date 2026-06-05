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

import org.junit.jupiter.api.Test;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for PropertiesCredentialsEnum.
 */
class PropertiesCredentialsEnumTest {

    @Test
    void testEnumValues() {
        assertNotNull(PropertiesCredentialsEnum.URL);
        assertNotNull(PropertiesCredentialsEnum.USERNAME);
        assertNotNull(PropertiesCredentialsEnum.PASSWORD);
        assertNotNull(PropertiesCredentialsEnum.TOKEN);
        assertNotNull(PropertiesCredentialsEnum.VERSION);
    }

    @Test
    void testGetName() {
        assertEquals("url", PropertiesCredentialsEnum.URL.getName());
        assertEquals("username", PropertiesCredentialsEnum.USERNAME.getName());
        assertEquals("password", PropertiesCredentialsEnum.PASSWORD.getName());
        assertEquals("token", PropertiesCredentialsEnum.TOKEN.getName());
        assertEquals("version", PropertiesCredentialsEnum.VERSION.getName());
    }

    @Test
    void testGetValueWithProperties() {
        Properties props = new Properties();
        props.setProperty("url", "https://test.salesforce.com");
        props.setProperty("username", "user@test.com");
        props.setProperty("password", "password123");
        props.setProperty("token", "token456");
        props.setProperty("version", "58.0");

        assertEquals("https://test.salesforce.com", PropertiesCredentialsEnum.URL.getValue(props));
        assertEquals("user@test.com", PropertiesCredentialsEnum.USERNAME.getValue(props));
        assertEquals("password123", PropertiesCredentialsEnum.PASSWORD.getValue(props));
        assertEquals("token456", PropertiesCredentialsEnum.TOKEN.getValue(props));
        assertEquals("58.0", PropertiesCredentialsEnum.VERSION.getValue(props));
    }

    @Test
    void testGetValueWithDefault() {
        Properties props = new Properties();

        String defaultUrl = "https://default.salesforce.com";
        String value = PropertiesCredentialsEnum.URL.getValue(props, defaultUrl);

        assertEquals(defaultUrl, value);
    }

    @Test
    void testGetValueWithPropertiesAndDefault() {
        Properties props = new Properties();
        props.setProperty("url", "https://test.salesforce.com");

        String value = PropertiesCredentialsEnum.URL.getValue(props, "https://default.salesforce.com");

        // Should return the actual value, not the default
        assertEquals("https://test.salesforce.com", value);
    }

    @Test
    void testGetValueMissingProperty() {
        Properties props = new Properties();

        // Getting a property that doesn't exist should return null
        String value = PropertiesCredentialsEnum.URL.getValue(props);
        assertNull(value);
    }

    @Test
    void testGetValueNullProperties() {
        assertThrows(NullPointerException.class, () -> {
            PropertiesCredentialsEnum.URL.getValue(null);
        });
    }

    @Test
    void testGetValueWithDefaultNullProperties() {
        assertThrows(NullPointerException.class, () -> {
            PropertiesCredentialsEnum.URL.getValue(null, "default");
        });
    }
}
