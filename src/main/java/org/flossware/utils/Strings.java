package org.flossware.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
//            TO DO:  Log an error
            throw new IllegalArgumentException(message);
        }

        return string;
    }

    public static String requireNonBlank(final String string) {
        return requireNonBlank(string, STRING_CANNOT_BE_BLANK);
    }


    /**
     * URL encodes <code>str</code>.
     */
    public static String asUrlEncoded(final String str) {
        try {
            return URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
//            TO DO:  Log an error

            throw new IllegalArgumentException("Trouble URL encoding [" + str + "]", ex);
        }
    }

    /**
     * Default constructor not allowed
     */
    private Strings() {
    }
}
