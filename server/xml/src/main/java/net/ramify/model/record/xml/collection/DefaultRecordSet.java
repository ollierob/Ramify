package net.ramify.model.record.xml.collection;

import com.google.common.base.MoreObjects;
import net.ramify.model.date.DateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;
import java.util.Set;

@XmlTransient
public class DefaultRecordSet implements RecordSet {

    private final RecordSetId id;
    private final RecordProto.SourceType source;
    private final RecordSetType type;
    private final DateRange date;
    private final PlaceId creatorPlaceId;
    private final PlaceId coversPlaceId;
    private final String longTitle, shortTitle;
    private final String description;
    private final int numRecords;
    private final int numIndividuals;
    private final Set<RecordSetReference> references;
    private final RecordSetId parent;
    private final Set<RecordSetId> next;

    DefaultRecordSet(
            final RecordSetId id,
            final RecordProto.SourceType source,
            final RecordSetType type,
            final DateRange date,
            final String longTitle,
            final PlaceId creatorPlaceId,
            final PlaceId coversPlaceId,
            final String shortTitle,
            final String description,
            final int numRecords,
            final int numIndividuals,
            final Set<RecordSetReference> references,
            final RecordSetId parent,
            final Set<RecordSetId> next) {
        this.id = id;
        this.source = source;
        this.type = Objects.requireNonNull(type, "type");
        this.date = Objects.requireNonNull(date, "date");
        this.creatorPlaceId = creatorPlaceId;
        this.coversPlaceId = Objects.requireNonNull(coversPlaceId, "covers");
        this.longTitle = longTitle;
        this.shortTitle = shortTitle;
        this.description = description;
        this.numRecords = numRecords;
        this.numIndividuals = numIndividuals;
        this.references = references;
        this.parent = parent;
        this.next = next;
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return id;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @CheckForNull
    @Override
    public PlaceId createdBy() {
        return creatorPlaceId;
    }

    @Nonnull
    @Override
    public PlaceId covers() {
        return coversPlaceId;
    }

    @Nonnull
    @Override
    public String title() {
        return longTitle;
    }

    @CheckForNull
    @Override
    public String description() {
        return description;
    }

    @Override
    public int numRecords() {
        return numRecords;
    }

    @Override
    public int numIndividuals() {
        return numIndividuals;
    }

    @Nonnull
    @Override
    public Set<RecordSetReference> references() {
        return references;
    }

    @CheckForNull
    public RecordSetId parentId() {
        return parent;
    }

    @CheckForNull
    public Set<RecordSetId> nextIds() {
        return next;
    }

    @Nonnull
    @Override
    public RecordSetType type() {
        return type;
    }

    @Nonnull
    @Override
    public RecordProto.RecordSet.Builder toProtoBuilder() {
        return RecordSet.super.toProtoBuilder()
                .setShortTitle(MoreObjects.firstNonNull(shortTitle, ""))
                .setSource(source);
    }

}
