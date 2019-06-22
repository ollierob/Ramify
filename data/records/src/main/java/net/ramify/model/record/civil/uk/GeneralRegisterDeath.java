package net.ramify.model.record.civil.uk;

import net.ramify.model.date.ExactDate;
import net.ramify.model.family.Family;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.civil.AbstractCivilRecord;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class GeneralRegisterDeath extends AbstractCivilRecord implements GeneralRegisterRecord {

    private final GeneralRegisterRecordDeathEntry died;
    private final GeneralRegisterRecordEntry informant;

    public GeneralRegisterDeath(
            final RecordId id,
            final ExactDate deathDate,
            @Nonnull final GeneralRegisterRecordDeathEntry died,
            @CheckForNull final GeneralRegisterRecordEntry informant) {
        super(id, deathDate);
        this.died = died;
        this.informant = informant;
    }

    @Nonnull
    ExactDate deathDate() {
        return this.date();
    }

    @Nonnull
    @Override
    public Family family() {
        final var died = this.died.build(this);
        return new SinglePersonFamily(died); //TODO informant
    }

}
