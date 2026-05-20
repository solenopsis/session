package org.solenopsis.session.login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test for LoginException.
 */
class LoginExceptionTest {

    @Test
    void testDefaultConstructor() {
        LoginException exception = new LoginException();
        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testMessageConstructor() {
        String message = "Login failed";
        LoginException exception = new LoginException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        String message = "Login failed due to network error";
        Throwable cause = new RuntimeException("Network timeout");
        LoginException exception = new LoginException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new RuntimeException("Authentication failed");
        LoginException exception = new LoginException(cause);

        assertNotNull(exception);
        assertSame(cause, exception.getCause());
        // Message will be the cause's toString()
        assertNotNull(exception.getMessage());
    }

    @Test
    void testExceptionIsRuntimeException() {
        LoginException exception = new LoginException("Test");
        // Verify it's a RuntimeException
        assert exception instanceof RuntimeException;
    }
}
