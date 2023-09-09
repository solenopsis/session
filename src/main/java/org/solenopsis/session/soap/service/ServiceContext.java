package org.solenopsis.session.soap.service;

import org.apache.cxf.service.Service;
import org.solenopsis.session.soap.api.ApiService;

/**
 *
 * @author sfloess
 */
public record ServiceContext(ApiService serviceType, Service service, Class portType) {
}
