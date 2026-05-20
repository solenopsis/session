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
