package org.solenopsis.session.soap;

import com.sforce.soap._2006._04.metadata.MetadataPortType;
import com.sforce.soap._2006._08.apex.ApexPortType;
import com.sforce.soap.tooling.SforceServicePortType;
import org.flossware.soap.Soap;

/**
 *
 * @author sfloess
 */
public enum PortClassEnum {
    APEX(ApexPortType.class),
    ENTERPRISE(Soap.class),
    PARTNER(com.sforce.soap.partner.Soap.class),
    METADATA(MetadataPortType.class),
    TOOLING(SforceServicePortType.class);

    final Class portType;

    PortClassEnum(final Class portType) {
        this.portType = portType;
    }

    public Class getPortType() {
        return portType;
    }
}
