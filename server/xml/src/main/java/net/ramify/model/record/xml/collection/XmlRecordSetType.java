package net.ramify.model.record.xml.collection;

import net.ramify.model.record.RecordSetTypes;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("ValidExternallyBoundObject")
@XmlType(namespace = XmlRecord.NAMESPACE)
enum XmlRecordSetType {

    @XmlEnumValue("birth")
    BIRTH(RecordSetTypes.BIRTH),

    @XmlEnumValue("baptism")
    BAPTISM(RecordSetTypes.BAPTISM),

    @XmlEnumValue("marriage")
    MARRIAGE(RecordSetTypes.MARRIAGE),

    @XmlEnumValue("mention")
    MENTION(RecordSetTypes.MENTION),

    @XmlEnumValue("census")
    CENSUS(RecordSetTypes.CENSUS),

    @XmlEnumValue("membership")
    MEMBERSHIP(RecordSetTypes.MEMBERSHIP),

    @XmlEnumValue("residence")
    RESIDENCE(RecordSetTypes.RESIDENCE),

    @XmlEnumValue("payment")
    PAYMENT(RecordSetTypes.PAYMENT),

    @XmlEnumValue("death")
    DEATH(RecordSetTypes.MARRIAGE),

    @XmlEnumValue("burial")
    BURIAL(RecordSetTypes.BURIAL),

    @XmlEnumValue("probate")
    PROBATE(RecordSetTypes.PROBATE),

    @XmlEnumValue("will")
    WILL(RecordSetTypes.WILL),

    @XmlEnumValue("mixed")
    MIXED(RecordSetTypes.MIXED);

    private final RecordSetType type;

    XmlRecordSetType(final RecordSetType type) {
        this.type = type;
    }

    @Nonnull
    RecordSetType type() {
        return type;
    }

}
