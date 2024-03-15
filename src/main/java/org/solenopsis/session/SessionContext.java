package org.solenopsis.session;

import org.flossware.commons.util.UrlUtil;
import org.solenopsis.soap.service.ServiceEnum;

/**
 * Represents data returned from Salesforce upon login.  Also used when one
 * performs a logout.
 *
 * @author sfloess
 */
public record SessionContext(
    String medataServerUrl,
    boolean isPasswordExpired,
    boolean isSandbox,
    String serverUrl,
    String sessionId,
    String userId,
    ServiceEnum service,
    Credentials credentials)
{
    // Sorta ugly but beats writing all the setters/getters!
    public SessionContext(String medataServerUrl, boolean isPasswordExpired, boolean isSandbox, String serverUrl, String sessionId, String userId, ServiceEnum service, Credentials credentials) {
        this.medataServerUrl = medataServerUrl;
        this.isPasswordExpired = isPasswordExpired;
        this.isSandbox = isSandbox;
        this.serverUrl = UrlUtil.computeHostUrlAsString(serverUrl);
        this.sessionId = sessionId;
        this.userId = userId;
        this.service = service;
        this.credentials = credentials;
    }
}