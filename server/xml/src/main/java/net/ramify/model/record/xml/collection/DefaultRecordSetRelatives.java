package net.ramify.model.record.xml.collection;

import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetRelatives;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
class DefaultRecordSetRelatives implements RecordSetRelatives {

    private final RecordSetId parent;
    private final RecordSetId previous, next;

    DefaultRecordSetRelatives(final RecordSetId parent, final RecordSetId previous, final RecordSetId next) {
        this.parent = parent;
        this.previous = previous;
        this.next = next;
    }

    @CheckForNull
    @Override
    public RecordSetId parent() {
        return parent;
    }

    @CheckForNull
    @Override
    public RecordSetId next() {
        return next;
    }

    @CheckForNull
    @Override
    public RecordSetId previous() {
        return previous;
    }

}
