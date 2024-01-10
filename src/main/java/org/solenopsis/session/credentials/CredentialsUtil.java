package org.solenopsis.session.credentials;

import org.solenopsis.session.Credentials;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.flossware.jcommons.io.IOException;
import org.flossware.jcommons.util.LoggerUtil;
import org.flossware.jcommons.util.PropertyUtil;

/**
 *
 * @author sfloess
 */
public final class CredentialsUtil {
        /**
     * Our logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CredentialsUtil.class.getName());


    /**
     * Return the logger.
     *
     * @return our logger.
     */
    static Logger getLogger() {
        return LOGGER;
    }
    
    private CredentialsUtil() {
    }

    public static Credentials fromProperties(final Properties properties) {
        Objects.requireNonNull(properties, "Null properties not allowed!");

        return new CredentialsRecord(
            PropertiesCredentialsEnum.URL.getValue(properties),
            PropertiesCredentialsEnum.USERNAME.getValue(properties),
            PropertiesCredentialsEnum.PASSWORD.getValue(properties),
            PropertiesCredentialsEnum.TOKEN.getValue(properties),
            PropertiesCredentialsEnum.VERSION.getValue(properties)
        );
    }

    public static Credentials fromInputStream(final InputStream inputStream, final boolean closeStream) {
        return fromProperties(PropertyUtil.fromInputStream(inputStream, closeStream));
    }

    public static Credentials fromInputStream(final InputStream inputStream) {
        return fromProperties(PropertyUtil.fromInputStream(inputStream));
    }

    public static Credentials fromReader(final InputStream inputStream, final boolean closeStream) {
        return fromProperties(PropertyUtil.fromInputStream(inputStream, closeStream));
    }

    public static Credentials fromReader(final InputStream inputStream) {
        return fromProperties(PropertyUtil.fromInputStream(inputStream));
    }

    public static Credentials fromResource(final String resource) {
        return fromInputStream(CredentialsUtil.class.getClassLoader().getResourceAsStream(resource), true);
    }

    public static Credentials fromFile(final File file) {
        try {
            return fromInputStream(new FileInputStream(file), true);
        } catch (final Exception exception) {
            LoggerUtil.log(getLogger(), Level.WARNING, "Trouble reading input stream!", exception);

            throw new IOException(exception);
        }

    }

    public static Credentials fromFile(final String file) {
        try {
            return fromInputStream(new FileInputStream(file), true);
        } catch (final Exception exception) {
            LoggerUtil.log(getLogger(), Level.WARNING, "Trouble reading input stream!", exception);

            throw new IOException(exception);
        }
    }
}
