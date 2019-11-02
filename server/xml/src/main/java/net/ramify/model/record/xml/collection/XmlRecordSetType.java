package net.ramify.model.record.xml.collection;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
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

    @XmlEnumValue("mention")
    MENTION,

    @XmlEnumValue("census")
    CENSUS(EventProto.RecordType.RESIDENCE),

    @XmlEnumValue("membership")
    MEMBERSHIP,

    @XmlEnumValue("residence")
    RESIDENCE,

    @XmlEnumValue("payment")
    PAYMENT,

    @XmlEnumValue("death")
    DEATH,

    @XmlEnumValue("burial")
    BURIAL,

    @XmlEnumValue("mixed")
    MIXED;

    private final EventProto.RecordType type;

    XmlRecordSetType() {
        this(null);
    }

    XmlRecordSetType(final EventProto.RecordType type) {
        this.type = type == null ? EventProto.RecordType.valueOf(this.name()) : type;
    }

    @Nonnull
    EventProto.RecordType type() {
        return type;
    }

}
