package net.ramify.model.record.xml.collection;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBetweenYears;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.Record;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.xml.RecordContext;
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
import java.util.Optional;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "recordSet")
class XmlRecordSet implements HasRecordSetId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "parentId", required = false)
    private String parentId;

    @XmlAttribute(name = "title", required = true)
    private String title;

    @XmlAttribute(name = "shortTitle")
    private String shortTitle;

    @XmlAttribute(name = "creatorPlace")
    private String creatorPlaceId;

    @XmlAttribute(name = "coversPlaceId")
    private String coversPlaceId;

    @XmlAttribute(name = "source", required = true)
    private XmlRecordSetSource source;

    @XmlAttribute(name = "type")
    private XmlRecordSetType type;

    @XmlElement(namespace = XmlRecord.NAMESPACE, name = "description")
    private String description;

    @XmlElement(name = "nextRecord", namespace = XmlRecord.NAMESPACE)
    private List<XmlRecordSetId> nextIds;

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

    Collection<DefaultRecordSet> build(final RecordSetProvider recordSets, final RecordContext context) {
        try {
            final var parent = this.parentRecordSet(recordSets);
            return this.build(parent, context);
        } catch (final Exception ex) {
            throw new RuntimeException("Error building record set " + id, ex);
        }
    }

    private Collection<DefaultRecordSet> build(final RecordSet parent, final RecordContext context) {
        final var self = this.buildSelf(parent, context);
        if (children == null) return Collections.singletonList(self);
        final var recordSets = Lists.<DefaultRecordSet>newArrayListWithExpectedSize(1 + 2 * children.size());
        recordSets.add(self);
        children.forEach(child -> recordSets.addAll(child.build(self, context)));
        return recordSets;
    }

    private DefaultRecordSet buildSelf(@CheckForNull final RecordSet parent, final RecordContext context) {
        return new DefaultRecordSet(
                this.recordSetId(),
                source.source(),
                this.type(parent),
                this.date(parent, context.dateParser()),
                title,
                this.creatorPlaceId(parent),
                this.coversPlaceId(parent),
                shortTitle,
                Functions.ifNonNull(description, String::trim),
                this.numRecords(),
                this.numIndividuals(),
                this.buildReferences(context),
                Functions.ifNonNull(parent, HasRecordSetId::recordSetId),
                this.nextIds());
    }

    EventProto.RecordType type(final RecordSet parent) {
        if (this.type != null) return this.type.type();
        if (parent != null) return parent.toProtoBuilder().getType();
        return EventProto.RecordType.UNSPECIFIED;
    }

    DateRange date(final RecordSet parent, final DateParser dateParser) {
        if (date != null) return date.resolve(dateParser);
        if (parent != null) return parent.date();
        return null;
    }

    @CheckForNull
    PlaceId creatorPlaceId(@CheckForNull final RecordSet parent) {
        if (creatorPlaceId != null) return new PlaceId(creatorPlaceId);
        return Functions.ifNonNull(parent, RecordSet::createdBy);
    }

    @CheckForNull
    PlaceId coversPlaceId(final RecordSet parent) {
        if (coversPlaceId != null) return new PlaceId(coversPlaceId);
        return Functions.ifNonNull(parent, RecordSet::covers);
    }

    XmlRecordSetType type() {
        return MoreObjects.firstNonNull(type, XmlRecordSetType.MIXED);
    }

    private Set<RecordSetReference> buildReferences(final RecordContext context) {
        if (references == null) return Collections.emptySet();
        return SetUtils.transform(references, ref -> ref.build(context.archives()));
    }

    private Set<RecordSetId> nextIds() {
        if (nextIds == null) return Collections.emptySet();
        return SetUtils.transform(nextIds, XmlRecordSetId::recordSetId);
    }

    private int numRecords() {
        if (records == null) return 0;
        return records.stream().mapToInt(XmlRecords::numRecords).sum();
    }

    private int numIndividuals() {
        if (records == null) return 0;
        return records.stream().mapToInt(XmlRecords::numIndividuals).sum();
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return new RecordSetId(id);
    }

    Optional<RecordSetId> parentRecordSetId() {
        return Optional.ofNullable(parentId).map(RecordSetId::new);
    }

    @CheckForNull
    RecordSet parentRecordSet(final RecordSetProvider recordSets) {
        return this.parentRecordSetId().map(recordSets::require).orElse(null);
    }

    Collection<Record> records(final RecordContext context) {
        return this.records(context, null); //FIXME pass in RSP and lookup from parent ID
    }

    Collection<Record> records(final RecordContext context, final RecordSet parent) {
        final var out = Lists.<Record>newArrayList();
        final var self = this.buildSelf(parent, context);
        if (records != null) records.forEach(record -> out.addAll(record.build(self, context)));
        if (children != null) children.forEach(child -> out.addAll(child.records(context, self)));
        return out;
    }

}
