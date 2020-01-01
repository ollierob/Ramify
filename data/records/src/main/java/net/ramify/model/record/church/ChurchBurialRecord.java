package net.ramify.model.record.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.RecordSetTypes;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.type.BurialRecord;

import javax.annotation.Nonnull;

public class ChurchBurialRecord extends AbstractChurchRecord implements BurialRecord {

    private final Family family;

    public ChurchBurialRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final PlaceId church,
            final Family family) {
        super(id, recordSet, date, family, church);
        this.family = family;
    }

    @Nonnull
    @Override
    public Family family() {
        return family;
    }

    @Override
    public RecordSetType type() {
        return RecordSetTypes.BURIAL;
    }

}
