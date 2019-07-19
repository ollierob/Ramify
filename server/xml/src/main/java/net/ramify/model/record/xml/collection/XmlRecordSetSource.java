package net.ramify.model.record.xml.collection;

import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@SuppressWarnings("ValidExternallyBoundObject")
@XmlType(namespace = XmlRecord.NAMESPACE)
enum XmlRecordSetSource {

    @XmlEnumValue("government")
    GOVERNMENT(RecordProto.SourceType.GOVERNMENT_SOURCE),

    @XmlEnumValue("church")
    CHURCH(RecordProto.SourceType.CHURCH_SOURCE),

    @XmlEnumValue("manor")
    MANOR(RecordProto.SourceType.MANOR_SOURCE);

    private final RecordProto.SourceType source;

    XmlRecordSetSource(final RecordProto.SourceType source) {
        this.source = Objects.requireNonNull(source);
    }

    @Nonnull
    RecordProto.SourceType source() {
        return source;
    }

}
