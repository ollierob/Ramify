package net.ramify.model.record.xml.collection;

import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("ValidExternallyBoundObject")
@XmlType(namespace = XmlRecord.NAMESPACE)
enum XmlRecordSetType {

    @XmlEnumValue("mixed")
    MIXED(RecordProto.RecordType.MIXED);

    private final RecordProto.RecordType type;

    XmlRecordSetType(final RecordProto.RecordType type) {
        this.type = type;
    }

    RecordProto.RecordType type() {
        return type;
    }

}
