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
package org.solenopsis.session.soap.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the SoapExceptionUtil class.
 *
 * @author sfloess
 */
public class SoapExceptionUtilTest {

    // ========== isIOException tests ==========

    @Test
    public void testIsIOException_WithNullThrowable() {
        assertFalse(SoapExceptionUtil.isIOException(null));
    }

    @Test
    public void testIsIOException_WithDirectIOException() {
        IOException ioException = new IOException("Test IO Exception");
        assertTrue(SoapExceptionUtil.isIOException(ioException));
    }

    @Test
    public void testIsIOException_WithWrappedIOException() {
        IOException ioException = new IOException("Test IO Exception");
        RuntimeException wrapper = new RuntimeException("Wrapper", ioException);
        assertTrue(SoapExceptionUtil.isIOException(wrapper));
    }

    @Test
    public void testIsIOException_WithDeeplyNestedIOException() {
        IOException ioException = new IOException("Deep IO Exception");
        Exception level1 = new Exception("Level 1", ioException);
        RuntimeException level2 = new RuntimeException("Level 2", level1);
        assertTrue(SoapExceptionUtil.isIOException(level2));
    }

    @Test
    public void testIsIOException_WithNonIOException() {
        RuntimeException exception = new RuntimeException("Not an IO Exception");
        assertFalse(SoapExceptionUtil.isIOException(exception));
    }

    @Test
    public void testIsIOException_WithNullCause() {
        RuntimeException exception = new RuntimeException("No cause");
        assertFalse(SoapExceptionUtil.isIOException(exception));
    }

    // ========== isServerUnavailable tests ==========

    @Test
    public void testIsServerUnavailable_StringWithNull() {
        assertFalse(SoapExceptionUtil.isServerUnavailable((String) null));
    }

    @Test
    public void testIsServerUnavailable_StringWithServerUnavailableMessage() {
        String message = "Error: SERVER_UNAVAILABLE - try again later";
        assertTrue(SoapExceptionUtil.isServerUnavailable(message));
    }

    @Test
    public void testIsServerUnavailable_StringWithoutServerUnavailableMessage() {
        String message = "Some other error message";
        assertFalse(SoapExceptionUtil.isServerUnavailable(message));
    }

    @Test
    public void testIsServerUnavailable_ThrowableWithNull() {
        assertFalse(SoapExceptionUtil.isServerUnavailable((Throwable) null));
    }

    @Test
    public void testIsServerUnavailable_ThrowableWithServerUnavailableMessage() {
        RuntimeException exception = new RuntimeException("SERVER_UNAVAILABLE");
        assertTrue(SoapExceptionUtil.isServerUnavailable(exception));
    }

    @Test
    public void testIsServerUnavailable_InvocationTargetException() {
        RuntimeException target = new RuntimeException("SERVER_UNAVAILABLE");
        InvocationTargetException exception = new InvocationTargetException(target);
        assertTrue(SoapExceptionUtil.isServerUnavailable(exception));
    }

    @Test
    public void testIsServerUnavailable_ThrowableWithoutMessage() {
        RuntimeException exception = new RuntimeException("Different error");
        assertFalse(SoapExceptionUtil.isServerUnavailable(exception));
    }

    // ========== isFunctionTemporarilyUnavailable tests ==========

    @Test
    public void testIsFunctionTemporarilyUnavailable_StringWithNull() {
        assertFalse(SoapExceptionUtil.isFunctionTemporarilyUnavailable((String) null));
    }

    @Test
    public void testIsFunctionTemporarilyUnavailable_StringWithMessage() {
        String message = "Error: FUNCTIONALITY_TEMPORARILY_UNAVAILABLE";
        assertTrue(SoapExceptionUtil.isFunctionTemporarilyUnavailable(message));
    }

    @Test
    public void testIsFunctionTemporarilyUnavailable_StringWithoutMessage() {
        String message = "Some other error";
        assertFalse(SoapExceptionUtil.isFunctionTemporarilyUnavailable(message));
    }

    @Test
    public void testIsFunctionTemporarilyUnavailable_ThrowableWithNull() {
        assertFalse(SoapExceptionUtil.isFunctionTemporarilyUnavailable((Throwable) null));
    }

    @Test
    public void testIsFunctionTemporarilyUnavailable_ThrowableWithMessage() {
        RuntimeException exception = new RuntimeException("FUNCTIONALITY_TEMPORARILY_UNAVAILABLE");
        assertTrue(SoapExceptionUtil.isFunctionTemporarilyUnavailable(exception));
    }

    @Test
    public void testIsFunctionTemporarilyUnavailable_InvocationTargetException() {
        RuntimeException target = new RuntimeException("FUNCTIONALITY_TEMPORARILY_UNAVAILABLE");
        InvocationTargetException exception = new InvocationTargetException(target);
        assertTrue(SoapExceptionUtil.isFunctionTemporarilyUnavailable(exception));
    }

    @Test
    public void testIsFunctionTemporarilyUnavailable_ThrowableWithoutMessage() {
        RuntimeException exception = new RuntimeException("Different error");
        assertFalse(SoapExceptionUtil.isFunctionTemporarilyUnavailable(exception));
    }

    // ========== isInvalidSessionId tests ==========

    @Test
    public void testIsInvalidSessionId_StringWithNull() {
        assertFalse(SoapExceptionUtil.isInvalidSessionId((String) null));
    }

    @Test
    public void testIsInvalidSessionId_StringWithInvalidSessionId() {
        String message = "Error: INVALID_SESSION_ID - please login again";
        assertTrue(SoapExceptionUtil.isInvalidSessionId(message));
    }

    @Test
    public void testIsInvalidSessionId_StringWithoutInvalidSessionId() {
        String message = "Some other error";
        assertFalse(SoapExceptionUtil.isInvalidSessionId(message));
    }

    @Test
    public void testIsInvalidSessionId_ExceptionWithNull() {
        assertFalse(SoapExceptionUtil.isInvalidSessionId((Exception) null));
    }

    @Test
    public void testIsInvalidSessionId_ExceptionWithMessage() {
        Exception exception = new Exception("INVALID_SESSION_ID");
        assertTrue(SoapExceptionUtil.isInvalidSessionId(exception));
    }

    @Test
    public void testIsInvalidSessionId_InvocationTargetException() {
        RuntimeException target = new RuntimeException("INVALID_SESSION_ID");
        InvocationTargetException exception = new InvocationTargetException(target);
        assertTrue(SoapExceptionUtil.isInvalidSessionId(exception));
    }

    @Test
    public void testIsInvalidSessionId_ExceptionAsInvocationTargetException() {
        RuntimeException target = new RuntimeException("INVALID_SESSION_ID");
        Exception exception = new InvocationTargetException(target);
        assertTrue(SoapExceptionUtil.isInvalidSessionId(exception));
    }

    @Test
    public void testIsInvalidSessionId_NestedExceptions() {
        assertTrue(SoapExceptionUtil.isInvalidSessionId(
            new Exception(new Throwable(new Throwable(new Throwable(SoapFailureMsgEnum.INVALID_SESSION_ID.getMsg()))))),
            "Should be an invalid session id!");
    }

    @Test
    public void testIsInvalidSessionId_ExceptionWithoutMessage() {
        assertFalse(SoapExceptionUtil.isInvalidSessionId(
            new Exception(new Throwable(new Throwable(new Throwable("Foo " + System.currentTimeMillis()))))),
            "Should NOT be an invalid session id!");
    }

    // ========== isInvalidQueryLocator tests ==========

    @Test
    public void testIsInvalidQueryLocator_StringWithNull() {
        assertFalse(SoapExceptionUtil.isInvalidQueryLocator((String) null));
    }

    @Test
    public void testIsInvalidQueryLocator_StringWithMessage() {
        String message = "Error: INVALID_QUERY_LOCATOR";
        assertTrue(SoapExceptionUtil.isInvalidQueryLocator(message));
    }

    @Test
    public void testIsInvalidQueryLocator_StringWithoutMessage() {
        String message = "Different error";
        assertFalse(SoapExceptionUtil.isInvalidQueryLocator(message));
    }

    @Test
    public void testIsInvalidQueryLocator_ThrowableWithNull() {
        assertFalse(SoapExceptionUtil.isInvalidQueryLocator((Throwable) null));
    }

    @Test
    public void testIsInvalidQueryLocator_ThrowableWithMessage() {
        RuntimeException exception = new RuntimeException("INVALID_QUERY_LOCATOR");
        assertTrue(SoapExceptionUtil.isInvalidQueryLocator(exception));
    }

    @Test
    public void testIsInvalidQueryLocator_InvocationTargetException() {
        RuntimeException target = new RuntimeException("INVALID_QUERY_LOCATOR");
        InvocationTargetException exception = new InvocationTargetException(target);
        assertTrue(SoapExceptionUtil.isInvalidQueryLocator(exception));
    }

    @Test
    public void testIsInvalidQueryLocator_ThrowableAsInvocationTargetException() {
        RuntimeException target = new RuntimeException("INVALID_QUERY_LOCATOR");
        Throwable exception = new InvocationTargetException(target);
        assertTrue(SoapExceptionUtil.isInvalidQueryLocator(exception));
    }

    @Test
    public void testIsInvalidQueryLocator_ThrowableWithoutMessage() {
        RuntimeException exception = new RuntimeException("Different error");
        assertFalse(SoapExceptionUtil.isInvalidQueryLocator(exception));
    }

    // ========== isResetSession tests ==========

    @Test
    public void testIsResetSession_WithInvalidSessionId() {
        Exception exception = new Exception("INVALID_SESSION_ID");
        assertTrue(SoapExceptionUtil.isResetSession(exception));
    }

    @Test
    public void testIsResetSession_WithIOException() {
        IOException ioException = new IOException("Connection error");
        Exception exception = new Exception("Wrapper", ioException);
        assertTrue(SoapExceptionUtil.isResetSession(exception));
    }

    @Test
    public void testIsResetSession_WithBothConditions() {
        IOException ioException = new IOException("INVALID_SESSION_ID");
        Exception exception = new Exception(ioException);
        assertTrue(SoapExceptionUtil.isResetSession(exception));
    }

    @Test
    public void testIsResetSession_WithNeitherCondition() {
        Exception exception = new Exception("Regular error");
        assertFalse(SoapExceptionUtil.isResetSession(exception));
    }

    @Test
    public void testIsResetSession_WithNull() {
        assertFalse(SoapExceptionUtil.isResetSession(null));
    }

    // ========== isRetry tests ==========

    @Test
    public void testIsRetry_WithServerUnavailable() {
        Throwable throwable = new RuntimeException("SERVER_UNAVAILABLE");
        assertTrue(SoapExceptionUtil.isRetry(throwable));
    }

    @Test
    public void testIsRetry_WithFunctionalityTemporarilyUnavailable() {
        Throwable throwable = new RuntimeException("FUNCTIONALITY_TEMPORARILY_UNAVAILABLE");
        assertTrue(SoapExceptionUtil.isRetry(throwable));
    }

    @Test
    public void testIsRetry_WithBothConditions() {
        Throwable throwable = new RuntimeException("SERVER_UNAVAILABLE and FUNCTIONALITY_TEMPORARILY_UNAVAILABLE");
        assertTrue(SoapExceptionUtil.isRetry(throwable));
    }

    @Test
    public void testIsRetry_WithNeitherCondition() {
        Throwable throwable = new RuntimeException("Different error");
        assertFalse(SoapExceptionUtil.isRetry(throwable));
    }

    @Test
    public void testIsRetry_WithNull() {
        assertFalse(SoapExceptionUtil.isRetry(null));
    }
}
