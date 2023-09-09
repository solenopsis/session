package org.flossware.soap;

import jakarta.xml.ws.Service;

/**
 *
 * @author sfloess
 */
public record Soap(Service service, Class portType) {
}
