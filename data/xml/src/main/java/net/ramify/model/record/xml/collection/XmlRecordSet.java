package net.ramify.model.record.xml.collection;

import com.google.common.collect.Lists;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBetweenYears;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.collections.SetUtils;
import net.ramify.utils.objects.Functions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @XmlElements({
            @XmlElement(name = "inYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "betweenYears", type = XmlBetweenYears.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange date;

    @XmlElementRef
    private List<XmlRecordSetReference> references;

    @XmlElementRef
    private List<XmlRecordSet> children;

    Collection<RecordSet> build(final DateParser dateParser) {
        return this.build(parentId == null ? null : new RecordSetId(parentId), dateParser);
    }

    Collection<RecordSet> build(final RecordSetId parentId, final DateParser dateParser) {
        final var self = new DefaultRecordSet(
                new RecordSetId(id),
                parentId,
                source.source(),
                type.type(),
                Functions.ifNonNull(date, d -> d.resolve(dateParser)),
                new PlaceId(place),
                title,
                Functions.ifNonNull(description, String::trim),
                this.buildReferences());
        if (children == null) return Collections.singletonList(self);
        final var recordSets = Lists.<RecordSet>newArrayListWithExpectedSize(1 + 2 * children.size());
        recordSets.add(self);
        children.forEach(child -> recordSets.addAll(child.build(self.recordSetId(), dateParser)));
        return recordSets;
    }

    private Set<RecordSetReference> buildReferences() {
        if (references == null) return Collections.emptySet();
        return SetUtils.transform(references, XmlRecordSetReference::build);
    }

}
