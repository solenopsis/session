package org.solenopsis.session;

/**
 *
 * @author sfloess
 */
public record Credentials(String url, String username, String password, String token, String securityPassword, String apiVersion) {

}
