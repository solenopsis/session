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
     * Determine if we server is unavailable - or contains one...
     *
     * @param throwable the failure to examine if its an IOException.
     *
     * @return true if throwable is an IOException or false if not.
     */
    public static boolean isServerUnavailable(final InvocationTargetException exception) {
        // Nope, get the cause and try again.
        return isServerUnavailable(exception.getTargetException().getMessage());
    }

    /**
     * Determine if we server is unavailable - or contains one...
     *
     * @param throwable the failure to examine if its an IOException.
     *
     * @return true if throwable is an IOException or false if not.
     */
    public static boolean isServerUnavailable(final Throwable throwable) {
        // If we reach the top of the exception stack, we are done...
        if (null == throwable) {
            return false;
        }

        // Is it an IOException?  If so, return true.
        if (throwable instanceof InvocationTargetException) {
            return isServerUnavailable((InvocationTargetException) throwable);
        }

        // Nope, get the cause and try again.
        return isServerUnavailable(throwable.getMessage());
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
     * Determine if the server returned functionality temporarily unavailable - or contains one...
     *
     * @param throwable the failure to examine if its an functionality temporarily unavailable.
     *
     * @return true if throwable is a functionality temporarily unavailable or false if not.
     */
    public static boolean isFunctionTemporarilyUnavailable(final InvocationTargetException exception) {
        // Nope, get the cause and try again.
        return isFunctionTemporarilyUnavailable(exception.getTargetException().getMessage());
    }

    /**
     * Determine if we server returned functionality temporarily unavailable - or contains one...
     *
     * @param throwable the failure to examine if functionality temporarily unavailable.
     *
     * @return true if throwable is functionality temporarily unavailable or false if not.
     */
    public static boolean isFunctionTemporarilyUnavailable(final Throwable throwable) {
        // If we reach the top of the exception stack, we are done...
        if (null == throwable) {
            return false;
        }

        // Is it an IOException?  If so, return true.
        if (throwable instanceof InvocationTargetException) {
            return isFunctionTemporarilyUnavailable((InvocationTargetException) throwable);
        }

        // Nope, get the cause and try again.
        return isFunctionTemporarilyUnavailable(throwable.getMessage());
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
        return isInvalidSessionId(failure.getTargetException().getMessage());
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
     * Determine if have a invalid query locator- or contains one...
     *
     * @param msg the failure to examine if its an IOException.
     *
     * @return true if msg is an IOException or false if not.
     */
    public static  boolean isInvalidQueryLocator(final String message) {
        return (message == null ? false : message.contains(SoapFailureMsgEnum.INVALID_QUERY_LOCATOR.getMsg()));
    }

    /**
     * Determine if have a invalid query locator- or contains one...
     *
     * @param InvocationTargetException the failure to examine if its an IOException.
     *
     * @return true if throwable is an IOException or false if not.
     */
    public static  boolean isInvalidQueryLocator(final InvocationTargetException exception) {
        return isInvalidQueryLocator(exception.getTargetException().getMessage());
    }

    /**
     * Determine if have a invalid query locator- or contains one...
     *
     * @param throwable the failure to examine if its an IOException.
     *
     * @return true if throwable is an IOException or false if not.
     */
    public static  boolean isInvalidQueryLocator(final Throwable throwable) {
        // If we reach the top of the exception stack, we are done...
        if (null == throwable) {
            return false;
        }

        // Is it an IOException?  If so, return true.
        if (throwable instanceof InvocationTargetException) {
            return isInvalidQueryLocator((InvocationTargetException) throwable);
        }

        // Nope, get the cause and try again.
        return isInvalidQueryLocator(throwable.getMessage());
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
     * @param remainingCalls the number of remaining calls.
     * @param failure        the failure that arose.
     *
     * @return true if we should retry the call or false if not.
     */
    public static boolean isRetry(final Throwable failure) {
        return (isServerUnavailable(failure) || isFunctionTemporarilyUnavailable(failure));
    }
}
