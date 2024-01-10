package org.solenopsis.session;

/**
 * Represents credential to log in to Salesforce.
 *
 * @author sfloess
 */
public interface Credentials {
    String url();

    String username();

    String token();

    String password();

    String version();

    default String securityPassword() {
        return password() + token();
    }
}
