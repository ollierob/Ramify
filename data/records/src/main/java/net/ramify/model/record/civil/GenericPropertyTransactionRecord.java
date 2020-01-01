package net.ramify.model.record.civil;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.record.DatedRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.RecordSetTypes;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.type.LifeEventRecord;

import javax.annotation.Nonnull;
import java.util.Set;

public class GenericPropertyTransactionRecord extends DatedRecord implements LifeEventRecord {

    private final Set<Family> families;

    public GenericPropertyTransactionRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final Set<Family> families) {
        super(id, recordSet, date);
        this.families = families;
    }

    @Override
    public RecordSetType type() {
        return RecordSetTypes.TRANSACTION;
    }

    @Nonnull
    @Override
    public Set<? extends Family> families() {
        return families;
    }

}
