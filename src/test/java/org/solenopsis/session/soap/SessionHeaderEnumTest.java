/* Copyright (C) 2023 Scot P. Floess
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
package org.solenopsis.session.soap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for SessionHeaderEnum.
 */
class SessionHeaderEnumTest {

    @Test
    void testEnumValues() {
        assertNotNull(SessionHeaderEnum.SESSION_HEADER);
        assertNotNull(SessionHeaderEnum.SESSION_ID);
    }

    @Test
    void testGetString() {
        assertEquals("SessionHeader", SessionHeaderEnum.SESSION_HEADER.getString());
        assertEquals("sessionId", SessionHeaderEnum.SESSION_ID.getString());
    }

    @Test
    void testSessionHeader() {
        SessionHeaderEnum header = SessionHeaderEnum.SESSION_HEADER;
        assertEquals("SessionHeader", header.getString());
    }

    @Test
    void testSessionId() {
        SessionHeaderEnum sessionId = SessionHeaderEnum.SESSION_ID;
        assertEquals("sessionId", sessionId.getString());
    }

    @Test
    void testValueOf() {
        SessionHeaderEnum header = SessionHeaderEnum.valueOf("SESSION_HEADER");
        assertEquals(SessionHeaderEnum.SESSION_HEADER, header);
    }

    @Test
    void testValues() {
        SessionHeaderEnum[] values = SessionHeaderEnum.values();
        assertEquals(2, values.length);
    }
}
