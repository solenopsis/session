package org.solenopsis.session.soap.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for SoapFailureMsgEnum.
 */
class SoapFailureMsgEnumTest {

    @Test
    void testEnumValues() {
        assertNotNull(SoapFailureMsgEnum.INVALID_SESSION_ID);
        assertNotNull(SoapFailureMsgEnum.SERVER_UNAVAILABLE);
        assertNotNull(SoapFailureMsgEnum.FUNCTIONALITY_TEMPORARILY_UNAVAILABLE);
        assertNotNull(SoapFailureMsgEnum.INVALID_QUERY_LOCATOR);
    }

    @Test
    void testGetMsg() {
        assertEquals("INVALID_SESSION_ID", SoapFailureMsgEnum.INVALID_SESSION_ID.getMsg());
        assertEquals("SERVER_UNAVAILABLE", SoapFailureMsgEnum.SERVER_UNAVAILABLE.getMsg());
        assertEquals("FUNCTIONALITY_TEMPORARILY_UNAVAILABLE",
                     SoapFailureMsgEnum.FUNCTIONALITY_TEMPORARILY_UNAVAILABLE.getMsg());
        assertEquals("INVALID_QUERY_LOCATOR", SoapFailureMsgEnum.INVALID_QUERY_LOCATOR.getMsg());
    }

    @Test
    void testInvalidSessionId() {
        SoapFailureMsgEnum msg = SoapFailureMsgEnum.INVALID_SESSION_ID;
        assertEquals("INVALID_SESSION_ID", msg.getMsg());
    }

    @Test
    void testServerUnavailable() {
        SoapFailureMsgEnum msg = SoapFailureMsgEnum.SERVER_UNAVAILABLE;
        assertEquals("SERVER_UNAVAILABLE", msg.getMsg());
    }

    @Test
    void testFunctionalityTemporarilyUnavailable() {
        SoapFailureMsgEnum msg = SoapFailureMsgEnum.FUNCTIONALITY_TEMPORARILY_UNAVAILABLE;
        assertEquals("FUNCTIONALITY_TEMPORARILY_UNAVAILABLE", msg.getMsg());
    }

    @Test
    void testInvalidQueryLocator() {
        SoapFailureMsgEnum msg = SoapFailureMsgEnum.INVALID_QUERY_LOCATOR;
        assertEquals("INVALID_QUERY_LOCATOR", msg.getMsg());
    }

    @Test
    void testValueOf() {
        SoapFailureMsgEnum msg = SoapFailureMsgEnum.valueOf("INVALID_SESSION_ID");
        assertEquals(SoapFailureMsgEnum.INVALID_SESSION_ID, msg);
    }

    @Test
    void testValues() {
        SoapFailureMsgEnum[] values = SoapFailureMsgEnum.values();
        assertEquals(4, values.length);
    }
}
