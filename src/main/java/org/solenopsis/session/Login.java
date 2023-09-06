package org.solenopsis.session;

/**
 *
 * @author sfloess
 */
public record Login(String medataServerUrl, boolean isPasswordExpired, boolean isSandbox, String serverUrl, String baseServerUrl, String sessionId, String userId, Credentials credentials) {

}
