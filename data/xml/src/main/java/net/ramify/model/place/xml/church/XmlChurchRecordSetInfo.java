package net.ramify.model.place.xml.church;

import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.provider.RecordSetProvider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "churchRecordSet")
public class XmlChurchRecordSetInfo implements HasRecordSetId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return new RecordSetId(id);
    }

    @CheckForNull
    public RecordSet build(final RecordSetProvider records) {
        return records.get(this.recordSetId());
    }

}
