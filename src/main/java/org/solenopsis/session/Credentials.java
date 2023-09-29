package org.solenopsis.session;

/**
 * Represents credential to log in to Salesforce.
 * 
 * @author sfloess
 */
public record Credentials(String url, String username, String password, String token, String securityPassword, String apiVersion) {

}
