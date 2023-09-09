package org.solenopsis.session.soap.service;

import org.apache.cxf.service.Service;

/**
 *
 * @author sfloess
 */
public record Soap(Service service, Class portType) {
}
