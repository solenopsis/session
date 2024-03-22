package org.solenopsis.session.soap;

/**
 *
 * @author sfloess
 */
public enum SessionHeaderEnum {
    SESSION_HEADER("SessionHeader"),
    SESSION_ID("sessionId");

    private final String str;

    private SessionHeaderEnum(final String str) {
        this.str = str;
    }

    public String getString() {
        return str;
    }
}
