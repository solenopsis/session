package org.solenopsis.session.credentials;

import org.solenopsis.session.Credentials;

/**
 * Represents credential to log in to Salesforce.
 *
 * @author sfloess
 */
public record CredentialsRecord(String url, String username, String password, String token, String version) implements Credentials {
}
