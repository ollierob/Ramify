package net.ramify.model.record.xml.collection;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

@XmlTransient
class DefaultRecordSet implements RecordSet {

    private final RecordSetId id;
    private final RecordSetId parentId;
    private final RecordProto.SourceType source;
    private final RecordProto.RecordType type;
    private final DateRange date;
    private final PlaceId placeId;
    private final String title;
    private final String description;
    private final Set<RecordSetReference> references;

    DefaultRecordSet(
            final RecordSetId id,
            final RecordSetId parentId,
            final RecordProto.SourceType source,
            final RecordProto.RecordType type,
            final DateRange date,
            final PlaceId placeId,
            final String title,
            final String description,
            final Set<RecordSetReference> references) {
        this.id = id;
        this.parentId = parentId;
        this.source = source;
        this.type = type;
        this.date = date;
        this.placeId = placeId;
        this.title = title;
        this.description = description;
        this.references = references;
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return id;
    }

    @CheckForNull
    @Override
    public RecordSetId parentId() {
        return parentId;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

    @Nonnull
    @Override
    public String title() {
        return title;
    }

    @CheckForNull
    @Override
    public String description() {
        return description;
    }

    @Nonnull
    @Override
    public Set<RecordSetReference> references() {
        return references;
    }

    @Nonnull
    @Override
    public RecordProto.RecordSet.Builder toProtoBuilder() {
        return RecordSet.super.toProtoBuilder()
                .setType(type)
                .setSource(source);
    }
}
