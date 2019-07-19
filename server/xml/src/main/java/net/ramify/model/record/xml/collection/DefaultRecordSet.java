package net.ramify.model.record.xml.collection;

import com.google.common.base.MoreObjects;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;
import java.util.Set;

@XmlTransient
public class DefaultRecordSet implements RecordSet {

    private final RecordSetId id;
    private final RecordSet parent;
    private final RecordProto.SourceType source;
    private final EventProto.RecordType type;
    private final DateRange date;
    private final PlaceId creatorPlaceId;
    private final PlaceId coversPlaceId;
    private final String longTitle, shortTitle;
    private final String description;
    private final int size;
    private final Set<RecordSetReference> references;

    public DefaultRecordSet(
            final RecordSetId id,
            final RecordSet parent,
            final RecordProto.SourceType source,
            final EventProto.RecordType type,
            final DateRange date,
            final String longTitle,
            final PlaceId creatorPlaceId,
            final PlaceId coversPlaceId,
            final String shortTitle,
            final String description,
            final int size,
            final Set<RecordSetReference> references) {
        this.id = id;
        this.parent = parent;
        this.source = source;
        this.type = type;
        this.date = Objects.requireNonNull(date, "date");
        this.creatorPlaceId = creatorPlaceId;
        this.coversPlaceId = coversPlaceId;
        this.longTitle = longTitle;
        this.shortTitle = shortTitle;
        this.description = description;
        this.size = size;
        this.references = references;
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return id;
    }

    @CheckForNull
    @Override
    public RecordSet parent() {
        return parent;
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
    public int size() {
        return size;
    }

    @Nonnull
    @Override
    public Set<RecordSetReference> references() {
        return references;
    }

    @Nonnull
    @Override
    public RecordProto.RecordSet.Builder toProtoBuilder(final boolean includeParent) {
        return RecordSet.super.toProtoBuilder(includeParent)
                .setShortTitle(MoreObjects.firstNonNull(shortTitle, ""))
                .setType(type)
                .setSource(source);
    }
}
