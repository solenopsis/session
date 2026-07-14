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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author sfloess
 */
public class SoapExceptionUtil {

    /**
     * Determine if we are an IOException - or contains one...
     *
     * @param throwable the failure to examine if its an IOException.
     *
     * @return true if throwable is an IOException or false if not.
     */
    public static boolean isIOException(final Throwable throwable) {
        // If we reach the top of the exception stack, we are done...
        if (null == throwable) {
            return false;
        }

        // Is it an IOException?  If so, return true.
        if (throwable instanceof IOException) {
            return true;
        }

        // Nope, get the cause and try again.
        return isIOException(throwable.getCause());
    }

    /**
     * Determine if msg contains server unavailable.
     *
     * @param msg the message to examine if it contains server unavailable.
     *
     * @return true if the msg contains server unavailable or false if not.
     */
    public static boolean isServerUnavailable(final String msg) {
        return (null == msg ? false : msg.contains(SoapFailureMsgEnum.SERVER_UNAVAILABLE.getMsg()));
    }

    /**
     * Determine if the server is unavailable based on the target exception.
     *
     * @param exception the InvocationTargetException to examine.
     *
     * @return true if the target exception indicates server unavailable or false if not.
     */
    public static boolean isServerUnavailable(final InvocationTargetException exception) {
        final Throwable target = exception.getTargetException();
        return (target == null) ? false : isServerUnavailable(target.getMessage());
    }

    /**
     * Determine if the throwable or any cause in its chain indicates server unavailable.
     *
     * @param throwable the failure to examine for server unavailable.
     *
     * @return true if throwable indicates server unavailable or false if not.
     */
    public static boolean isServerUnavailable(final Throwable throwable) {
        if (null == throwable) {
            return false;
        }

        if (throwable instanceof InvocationTargetException) {
            return isServerUnavailable((InvocationTargetException) throwable);
        }

        if (isServerUnavailable(throwable.getMessage())) {
            return true;
        }

        return isServerUnavailable(throwable.getCause());
    }

    /**
     * Determine if msg contains functionality temporarily unavailable.
     *
     * @param msg the message to examine if it contains functionality temporarily unavailable.
     *
     * @return true if the msg contains functionality temporarily unavailable or false if not.
     */
    public static boolean isFunctionTemporarilyUnavailable(final String msg) {
        return (null == msg ? false : msg.contains(SoapFailureMsgEnum.FUNCTIONALITY_TEMPORARILY_UNAVAILABLE.getMsg()));
    }

    /**
     * Determine if the server returned functionality temporarily unavailable.
     *
     * @param exception the InvocationTargetException to examine.
     *
     * @return true if the target exception indicates functionality temporarily unavailable or false if not.
     */
    public static boolean isFunctionTemporarilyUnavailable(final InvocationTargetException exception) {
        final Throwable target = exception.getTargetException();
        return (target == null) ? false : isFunctionTemporarilyUnavailable(target.getMessage());
    }

    /**
     * Determine if the throwable or any cause in its chain indicates functionality temporarily unavailable.
     *
     * @param throwable the failure to examine for functionality temporarily unavailable.
     *
     * @return true if throwable indicates functionality temporarily unavailable or false if not.
     */
    public static boolean isFunctionTemporarilyUnavailable(final Throwable throwable) {
        if (null == throwable) {
            return false;
        }

        if (throwable instanceof InvocationTargetException) {
            return isFunctionTemporarilyUnavailable((InvocationTargetException) throwable);
        }

        if (isFunctionTemporarilyUnavailable(throwable.getMessage())) {
            return true;
        }

        return isFunctionTemporarilyUnavailable(throwable.getCause());
    }


    /**
     * Return true if message contains invalid session id.
     *
     * @param message is the message to examine for being an invalid session id.
     */
    public static boolean isInvalidSessionId(final String message) {
        return (null == message ? false : message.contains(SoapFailureMsgEnum.INVALID_SESSION_ID.getMsg()));
    }

    /**
     * Return true if we have an invalid session id or false if not.
     *
     * @param failure is the failure to examine for an invalid session id.
     */
    public static boolean isInvalidSessionId(final InvocationTargetException failure) {
        final Throwable target = failure.getTargetException();
        return (target == null) ? false : isInvalidSessionId(target.getMessage());
    }

    /**
     * Return true if we have an invalid session id or false if not.
     *
     * @param failure is the failure to examine for an invalid session id.
     */
    public static boolean isInvalidSessionId(final Exception failure) {
        if (null == failure) {
            return false;
        }

        if (failure instanceof InvocationTargetException) {
            return isInvalidSessionId((InvocationTargetException) failure);
        }

        return isInvalidSessionId(failure.getMessage());
    }

    /**
     * Determine if the message indicates an invalid query locator.
     *
     * @param message the message to examine for invalid query locator.
     *
     * @return true if the message contains invalid query locator or false if not.
     */
    public static boolean isInvalidQueryLocator(final String message) {
        return (message == null ? false : message.contains(SoapFailureMsgEnum.INVALID_QUERY_LOCATOR.getMsg()));
    }

    /**
     * Determine if the exception indicates an invalid query locator.
     *
     * @param exception the InvocationTargetException to examine.
     *
     * @return true if the target exception indicates invalid query locator or false if not.
     */
    public static boolean isInvalidQueryLocator(final InvocationTargetException exception) {
        final Throwable target = exception.getTargetException();
        return (target == null) ? false : isInvalidQueryLocator(target.getMessage());
    }

    /**
     * Determine if the throwable or any cause in its chain indicates an invalid query locator.
     *
     * @param throwable the failure to examine for invalid query locator.
     *
     * @return true if throwable indicates invalid query locator or false if not.
     */
    public static boolean isInvalidQueryLocator(final Throwable throwable) {
        if (null == throwable) {
            return false;
        }

        if (throwable instanceof InvocationTargetException) {
            return isInvalidQueryLocator((InvocationTargetException) throwable);
        }

        if (isInvalidQueryLocator(throwable.getMessage())) {
            return true;
        }

        return isInvalidQueryLocator(throwable.getCause());
    }

    /**
     * Return true if we have an exception that warrants a reset session. A reset should
     * occur if exception is an invalid session id or its some subclass of IOException.
     *
     * @param exception the exception that arose that may require a session reset.
     *
     * @return true if we should reset our session based upon exception or false if not.
     */
    public static boolean isResetSession(final Exception exception) {
        return isInvalidSessionId(exception) || isIOException(exception);
    }

    /**
     * Should we retry the call?
     *
     * @param failure the failure that arose.
     *
     * @return true if we should retry the call or false if not.
     */
    public static boolean isRetry(final Throwable failure) {
        return (isServerUnavailable(failure) || isFunctionTemporarilyUnavailable(failure));
    }
}
