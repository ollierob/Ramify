package net.ramify.model.record.civil.uk;

import net.ramify.model.date.ExactDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.civil.AbstractCivilRecord;
import net.ramify.model.record.type.DeathRecord;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class GeneralRegisterDeath extends AbstractCivilRecord implements GeneralRegisterRecord, DeathRecord {

    private final PlaceId deathPlace;
    private final GeneralRegisterRecordDeathEntry deceased;
    private final GeneralRegisterRecordDeathInformant informant;

    public GeneralRegisterDeath(
            final RecordId id,
            final PlaceId deathPlace,
            final ExactDate deathDate,
            @Nonnull final GeneralRegisterRecordDeathEntry deceased,
            @CheckForNull final GeneralRegisterRecordDeathInformant informant) {
        super(id, deathDate);
        this.deathPlace = deathPlace;
        this.deceased = deceased;
        this.informant = informant;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return deathPlace;
    }

    @Nonnull
    ExactDate deathDate() {
        return this.date();
    }

    @Nonnull
    @Override
    public Family family() {
        final var deceased = this.deceased.build(this);
        if (informant == null) return new SinglePersonFamily(deceased);
        final var informant = this.informant.build(this);
        return new FamilyBuilder()
                .addRelationship(deceased, informant, this.informant.relationshipToDeceased())
                .build();
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.DEATH;
    }

}
