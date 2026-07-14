/*
 * Copyright (C) 2023-2026 Scot P. Floess
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.solenopsis.session;

import java.util.Objects;
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
        this.serverUrl = UrlUtil.computeHostUrlAsString(Objects.requireNonNull(serverUrl, "serverUrl cannot be null"));
        this.sessionId = sessionId;
        this.userId = userId;
        this.service = service;
        this.credentials = credentials;
    }

    public SessionContext createNewSessionContext(final String newSessionId) {
        return new SessionContext(medataServerUrl, isPasswordExpired, isSandbox, serverUrl, newSessionId, userId, service, credentials);
    }

    @Override
    public String toString() {
        return "SessionContext[medataServerUrl=" + medataServerUrl + ", isPasswordExpired=" + isPasswordExpired
            + ", isSandbox=" + isSandbox + ", serverUrl=" + serverUrl + ", sessionId=*****, userId=" + userId
            + ", service=" + service + ", credentials=" + credentials + "]";
    }
}