package net.ramify.model.record.xml.collection;

import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("ValidExternallyBoundObject")
@XmlType(namespace = XmlRecord.NAMESPACE)
enum XmlRecordSetType {

    @XmlEnumValue("birth")
    BIRTH,

    @XmlEnumValue("baptism")
    BAPTISM,

    @XmlEnumValue("marriage")
    MARRIAGE,

    @XmlEnumValue("census")
    CENSUS(RecordProto.RecordType.RESIDENCE),

    @XmlEnumValue("death")
    DEATH,

    @XmlEnumValue("burial")
    BURIAL,

    @XmlEnumValue("mixed")
    MIXED;

    private final RecordProto.RecordType type;

    XmlRecordSetType() {
        this(null);
    }

    XmlRecordSetType(final RecordProto.RecordType type) {
        this.type = type;
    }

    RecordProto.RecordType type() {
        return type == null ? RecordProto.RecordType.valueOf(this.name()) : type;
    }

}
