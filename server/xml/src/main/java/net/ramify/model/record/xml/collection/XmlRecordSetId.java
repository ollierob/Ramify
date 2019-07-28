package net.ramify.model.record.xml.collection;

import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "recordSetId", namespace = XmlRecord.NAMESPACE)
public class XmlRecordSetId implements HasRecordSetId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return new RecordSetId(id);
    }

}
