package org.flossware.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Same vein as <code>java.util.Objects</code>
 * 
 * This utility class will be moved to a FlossWare project.
 *
 * @author sfloess
 */
public class Strings {
    public static final String STRING_CANNOT_BE_BLANK = "String cannot be blank!";

    public static String requireNonBlank(final String string, final String message) {
        if (StringUtils.isBlank(string)) {
            throw new IllegalArgumentException(message);
        }

        return string;
    }

    public static String requireNonBlank(final String string) {
        return requireNonBlank(string, STRING_CANNOT_BE_BLANK);
    }

    private Strings() {
    }
}
