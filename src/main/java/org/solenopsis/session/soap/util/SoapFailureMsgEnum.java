package org.solenopsis.session.soap.util;

/**
 *
 * @author sfloess
 */
public enum SoapFailureMsgEnum {
    INVALID_SESSION_ID("INVALID_SESSION_ID"),
    SERVER_UNAVAILABLE("SERVER_UNAVAILABLE"),
    FUNCTIONALITY_TEMPORARILY_UNAVAILABLE("FUNCTIONALITY_TEMPORARILY_UNAVAILABLE"),
    INVALID_QUERY_LOCATOR("INVALID_QUERY_LOCATOR");

    private final String msg;

    private SoapFailureMsgEnum(final String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
