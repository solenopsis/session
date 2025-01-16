package org.solenopsis.session.soap.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Test the SoapExceptionUtil class.
 *
 * @author sfloess
 */
public class SoapExceptionUtilTest {
    @Test
    public void test_isInvalidSessionId() {
        assertTrue(SoapExceptionUtil.isInvalidSessionId(new Exception(new Throwable(new Throwable(new Throwable(SoapFailureMsgEnum.INVALID_SESSION_ID.getMsg()))))), "Should be an invalid session id!");
        
        assertFalse(SoapExceptionUtil.isInvalidSessionId(new Exception(new Throwable(new Throwable(new Throwable("Foo " + System.currentTimeMillis()))))), "Should NOT be an invalid session id!");
    }
}
