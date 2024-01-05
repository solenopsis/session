package org.solenopsis.session.credentials;

import java.util.Objects;
import java.util.Properties;

/**
 * Represents credential to log in to Salesforce.
 *
 * @author sfloess
 */
public record CredentialsRecord(String url, String username, String password, String token, String version) implements Credentials {
    public CredentialsRecord fromProperties(final Properties properties) {
        Objects.requireNonNull(properties, "Null properties not allowed!");

        return new CredentialsRecord(
            PropertiesCredentialsEnum.URL.getValue(properties),
            PropertiesCredentialsEnum.USERNAME.getValue(properties),
            PropertiesCredentialsEnum.PASSWORD.getValue(properties),
            PropertiesCredentialsEnum.TOKEN.getValue(properties),
            PropertiesCredentialsEnum.VERSION.getValue(properties)
        );
    }
}
