package org.solenopsis.session.credentials;

import java.util.Objects;
import java.util.Properties;

/**
 *
 * @author sfloess
 */
public enum PropertiesCredentialsEnum {
    URL("url"),
    USERNAME("username"),
    PASSWORD("password"),
    TOKEN("token"),
    VERSION("version"),
    ;

    private final String name;

    private PropertiesCredentialsEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue(final Properties properties, final String defaultValue) {
        return Objects.requireNonNull(properties, "Null properties not allowed!").getProperty(getName(), defaultValue);
    }

    public String getValue(final Properties properties) {
        return Objects.requireNonNull(properties, "Null properties not allowed!").getProperty(getName());
    }
}
