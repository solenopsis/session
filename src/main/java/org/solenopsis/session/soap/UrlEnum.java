/*
 * Copyright (C) 2017 Scot P. Floess
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
package org.solenopsis.session.soap;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import java.util.Objects;
import java.util.function.BiFunction;
import org.flossware.jcommons.util.MethodUtil;
import org.solenopsis.session.Session;

/**
 * Computes URLs for a service and session.
 *
 * @author Scot P. Floess
 */
public enum UrlEnum {
    APEX("services/Soap/s", (service, session) -> session.credentials().version()),
    CUSTOM("services/Soap/class", (service, session) -> MethodUtil.findMethodsForAnnotationClass(service.getClass(), WebEndpoint.class).get(0).getName()),
    ENTERPRISE("services/Soap/c", (service, session) -> session.credentials().version()),
    METADATA("services/Soap/m", (service, session) -> session.credentials().version()),
    PARTNER("services/Soap/u", (service, session) -> session.credentials().version()),
    TOOLING("services/Soap/T", (service, session) -> session.credentials().version());

    /**
     * The partial URL.
     */
    private final String partialUrl;

    private final BiFunction<Service, Session, String> urlFunction;

    /**
     * This constructor sets the SFDC web service, port type and partial URL (as defined in the Java doc header).
     *
     * @param webServiceType       the SFDC web service.
     * @param sessionServerFactory is the factory that can compute a server name for a session.
     * @param webServiceSubUrl     the port for the web service.
     */
    private UrlEnum(final String partialUrl, BiFunction<Service, Session, String> urlFunction) {
        this.partialUrl = partialUrl;
        this.urlFunction = urlFunction;
    }

    /**
     * Computes the URL based upon service and session.
     */
    BiFunction<Service, Session, String>getUrlFunction() {
        return urlFunction;
    }

    /**
     * Return our partial URL.
     */
    public String getPartialUrl() {
        return partialUrl;
    }

    public String computeUrl(final Service service, final Session session) {
        return getPartialUrl() + "/" + getUrlFunction().apply(Objects.requireNonNull(service, "Service cannot be null!"), Objects.requireNonNull(session, "Session cannot be null!"));
    }
}
