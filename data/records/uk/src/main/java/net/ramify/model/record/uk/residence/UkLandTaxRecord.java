package net.ramify.model.record.uk.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Histories;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.event.Residence;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.address.Address;
import net.ramify.model.record.NonFamilyRecord;
import net.ramify.model.record.uk.UkMoney;

import javax.annotation.Nonnull;
import java.util.Map;

public class UkLandTaxRecord implements NonFamilyRecord {

    private final DateRange date;
    private final PersonId owner;
    private final PersonId occupier;
    private final Address address;
    private final UkMoney tax;

    public UkLandTaxRecord(
            final DateRange date,
            final PersonId owner,
            final PersonId occupier,
            final Address address,
            final UkMoney tax) {
        this.date = date;
        this.owner = owner;
        this.occupier = occupier;
        this.address = address;
        this.tax = tax;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public Histories histories() {
        return Histories.of(Map.of(
                owner, PersonalEvents.of(owner),
                occupier, PersonalEvents.of(occupier, new Residence(date, "Land tax", address))));
    }

}
