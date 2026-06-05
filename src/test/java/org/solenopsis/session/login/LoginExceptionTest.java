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
