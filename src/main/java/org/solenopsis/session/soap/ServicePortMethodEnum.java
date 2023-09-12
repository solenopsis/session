package org.solenopsis.session.soap;

import jakarta.xml.ws.WebEndpoint;
import java.lang.reflect.Method;

/**
 *
 * @author sfloess
 */
public enum ServicePortMethodEnum {
    APEX(ServiceEnum.APEX),
    ENTERPRISE(ServiceEnum.ENTERPRISE),
    METADATA(ServiceEnum.METADATA),
    PARTNER(ServiceEnum.PARTNER),
    TOOLING(ServiceEnum.TOOLING)
    ;

    private final Method portMethod;

    private ServicePortMethodEnum(final Method[] methods) {
        Method toSet = null;

        for (final Method method : methods) {
            if (null != method.getAnnotation(WebEndpoint.class) && 0 == method.getParameterCount()) {
                toSet = method;

                break;
            }
        }

        this.portMethod = toSet;
    }

    private ServicePortMethodEnum(final ServiceEnum service) {
        this(service.getService().getClass().getMethods());
    }

    public Method getPortMethod() {
        return portMethod;
    }
}
