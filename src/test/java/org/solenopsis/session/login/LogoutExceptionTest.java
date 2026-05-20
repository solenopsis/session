package org.solenopsis.session.login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test for LogoutException.
 */
class LogoutExceptionTest {

    @Test
    void testDefaultConstructor() {
        LogoutException exception = new LogoutException();
        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testMessageConstructor() {
        String message = "Logout failed";
        LogoutException exception = new LogoutException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        String message = "Logout failed due to session error";
        Throwable cause = new RuntimeException("Session expired");
        LogoutException exception = new LogoutException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new RuntimeException("Connection closed");
        LogoutException exception = new LogoutException(cause);

        assertNotNull(exception);
        assertSame(cause, exception.getCause());
        // Message will be the cause's toString()
        assertNotNull(exception.getMessage());
    }

    @Test
    void testExceptionIsRuntimeException() {
        LogoutException exception = new LogoutException("Test");
        // Verify it's a RuntimeException
        assert exception instanceof RuntimeException;
    }
}
