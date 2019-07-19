package net.ramify.model.record.xml.collection;

import com.google.common.collect.Lists;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBetweenYears;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
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
import java.util.Objects;
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

    @XmlAttribute(name = "shortTitle", required = false)
    private String shortTitle;

    @XmlAttribute(name = "creatorPlace", required = false)
    private String creatorPlaceId;

    @XmlAttribute(name = "coversPlaceId", required = false)
    private String coversPlaceId;

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

    Collection<RecordSet> build(final RecordSetProvider recordSets, final RecordContext context) {
        try {
            final var parent = Functions.ifNonNull(this.parentRecordSetId(), recordSets::require);
            return this.build(parent, context);
        } catch (final Exception ex) {
            throw new RuntimeException("Error building record set " + id, ex);
        }
    }

    Collection<RecordSet> build(final RecordSet parent, final RecordContext context) {
        final var self = this.buildSelf(parent, context);
        if (children == null) return Collections.singletonList(self);
        final var recordSets = Lists.<RecordSet>newArrayListWithExpectedSize(1 + 2 * children.size());
        recordSets.add(self);
        children.forEach(child -> recordSets.addAll(child.build(self, context)));
        return recordSets;
    }

    private RecordSet buildSelf(@CheckForNull final RecordSet parent, final RecordContext context) {
        return new DefaultRecordSet(
                this.recordSetId(),
                parent,
                source.source(),
                type.type(),
                this.date(parent, context.dateParser()),
                title,
                this.creatorPlaceId(parent),
                this.coversPlaceId(parent),
                shortTitle,
                Functions.ifNonNull(description, String::trim),
                this.size(),
                this.buildReferences(context));
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

    PlaceId coversPlaceId(final RecordSet parent) {
        if (coversPlaceId != null) return new PlaceId(coversPlaceId);
        return Functions.ifNonNull(parent, RecordSet::covers);
    }

    private Set<RecordSetReference> buildReferences(final RecordContext context) {
        if (references == null) return Collections.emptySet();
        return SetUtils.transform(references, ref -> ref.build(context.archives()));
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

    Collection<Record> records(final RecordContext context) {
        if (records == null) return Collections.emptySet();
        final var records = Lists.<Record>newArrayList();
        final var self = this.buildSelf(null, context); //FIXME null parent as record sets may not yet have been generated
        this.records.forEach(record -> records.addAll(record.build(self, context)));
        return records;
    }

    Optional<XmlRecordSet> find(final RecordSetId id) {
        if (this.id.equals(id.value())) return Optional.of(this);
        if (children == null || children.isEmpty()) return Optional.empty();
        return children.stream().map(child -> child.find(id).orElse(null)).filter(Objects::nonNull).findAny();
    }

}
