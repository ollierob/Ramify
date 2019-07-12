package net.ramify.model.place.xml.church;

import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RenamedRecordSet;
import net.ramify.model.record.provider.RecordSetProvider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "churchRecordSet")
public class XmlChurchRecordSetInfo implements HasRecordSetId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "title", required = false)
    private String title;

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return new RecordSetId(id);
    }

    @CheckForNull
    public RecordSet build(final RecordSetProvider records) {
        final var recordSet = records.get(this.recordSetId());
        return recordSet != null && title != null
                ? new RenamedRecordSet(recordSet, title)
                : recordSet;
    }

}
