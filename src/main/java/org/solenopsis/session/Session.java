package org.solenopsis.session;

import org.solenopsis.soap.SubUrlEnum;

/**
 * Represents data returned from Salesforce upon login.  Also used when one
 * performs a logout.
 *
 * @author sfloess
 */
public record Session(
    String medataServerUrl,
    boolean isPasswordExpired,
    boolean isSandbox,
    String serverUrl,
    SubUrlEnum baseServerUrl,
    String sessionId,
    String userId,
    Credentials credentials)
{
}
