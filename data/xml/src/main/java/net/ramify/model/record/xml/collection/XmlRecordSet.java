package net.ramify.model.record.xml.collection;

import com.google.common.collect.Lists;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "recordSet")
class XmlRecordSet {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "parentId", required = false)
    private String parentId;

    @XmlAttribute(name = "title", required = true)
    private String title;

    @XmlAttribute(name = "place", required = true)
    private String place;

    @XmlAttribute(name = "source", required = true)
    private XmlRecordSetSource source;

    @XmlAttribute(name = "type", required = true)
    private XmlRecordSetType type;

    @XmlElement(namespace = XmlRecord.NAMESPACE, name = "description", required = false)
    private String description;

    @XmlElementRef
    private List<XmlRecordSet> children;

    Collection<RecordSet> build() {
        return this.build(parentId == null ? null : new RecordSetId(parentId));
    }

    Collection<RecordSet> build(final RecordSetId parentId) {
        final var self = new DefaultRecordSet(
                new RecordSetId(id),
                parentId,
                source.source(),
                type.type(),
                null, //TODO
                new PlaceId(place),
                title,
                description);
        if (children == null) return Collections.singletonList(self);
        final var recordSets = Lists.<RecordSet>newArrayListWithExpectedSize(1 + 2 * children.size());
        recordSets.add(self);
        children.forEach(child -> recordSets.addAll(child.build(self.recordSetId())));
        return recordSets;
    }

}
