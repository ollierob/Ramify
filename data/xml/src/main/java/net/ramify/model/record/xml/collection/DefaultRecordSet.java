package net.ramify.model.record.xml.collection;

import net.ramify.model.date.DateRange;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
class DefaultRecordSet implements RecordSet {

    private final RecordSetId id;
    private final RecordSetId parentId;
    private final RecordProto.RecordType type;
    private final DateRange date;
    private final String title;
    private final String description;

    DefaultRecordSet(
            final RecordSetId id,
            final RecordSetId parentId,
            final RecordProto.RecordType type,
            final DateRange date,
            final String title,
            final String description) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.date = date;
        this.title = title;
        this.description = description;
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
    public RecordProto.RecordType protoType() {
        return type;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
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

}
