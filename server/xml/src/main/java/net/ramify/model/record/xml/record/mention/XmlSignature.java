package net.ramify.model.record.xml.record.mention;

import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(namespace = XmlRecord.NAMESPACE)
public enum XmlSignature {

    @XmlEnumValue("signature")
    SIGNATURE,

    @XmlEnumValue("mark")
    MARK;

    public boolean isLiterate() {
        return this == SIGNATURE;
    }

}
