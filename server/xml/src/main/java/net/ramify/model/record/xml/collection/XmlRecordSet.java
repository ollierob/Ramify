package net.ramify.model.record.xml.collection;

import com.google.common.collect.Lists;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBetweenYears;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.Record;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.SetUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
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
class XmlRecordSet implements HasRecordSetId, HasPlaceId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "parentId", required = false)
    private String parentId;

    @XmlAttribute(name = "title", required = true)
    private String title;

    @XmlAttribute(name = "shortTitle", required = false)
    private String shortTitle;

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

    @XmlElementRef
    private List<XmlRecords> records;

    Collection<RecordSet> build(final RecordSetProvider recordSets, final DateParser dateParser) {
        final var parent = Functions.ifNonNull(this.parentRecordSetId(), recordSets::require);
        return this.build(parent, dateParser);
    }

    Collection<RecordSet> build(final RecordSet parent, final DateParser dateParser) {
        final var self = this.buildSelf(parent, dateParser);
        if (children == null) return Collections.singletonList(self);
        final var recordSets = Lists.<RecordSet>newArrayListWithExpectedSize(1 + 2 * children.size());
        recordSets.add(self);
        children.forEach(child -> recordSets.addAll(child.build(self, dateParser)));
        return recordSets;
    }

    private RecordSet buildSelf(final RecordSet parent, final DateParser dateParser) {
        return new DefaultRecordSet(
                this.recordSetId(),
                parent,
                source.source(),
                type.type(),
                Functions.ifNonNull(date, d -> d.resolve(dateParser)),
                placeId(),
                title,
                shortTitle,
                Functions.ifNonNull(description, String::trim),
                this.size(),
                this.buildReferences());
    }

    @Override
    public PlaceId placeId() {
        return new PlaceId(place);
    }

    private Set<RecordSetReference> buildReferences() {
        if (references == null) return Collections.emptySet();
        return SetUtils.transform(references, XmlRecordSetReference::build);
    }

    private int size() {
        if (records == null) return 0;
        return records.stream().mapToInt(XmlRecords::size).sum();
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return new RecordSetId(id);
    }

    @CheckForNull
    RecordSetId parentRecordSetId() {
        return Functions.ifNonNull(parentId, RecordSetId::new);
    }

    Collection<Record> records(final PlaceProvider places, final NameParser nameParser, final DateParser dateParser) {
        if (records == null) return Collections.emptySet();
        final var records = Lists.<Record>newArrayList();
        final var self = this.buildSelf(null, dateParser);
        this.records.forEach(record -> records.addAll(record.build(self, places, nameParser, dateParser)));
        return records;
    }

}