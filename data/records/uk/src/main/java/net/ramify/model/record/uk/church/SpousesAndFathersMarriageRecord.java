package net.ramify.model.record.uk.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.Histories;
import net.ramify.model.event.Marriage;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.family.Family;
import net.ramify.model.family.relationship.MarriedCouple;
import net.ramify.model.family.relationship.ParentChild;
import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.PersonalAttributes;
import net.ramify.model.place.address.Address;
import net.ramify.model.place.address.HasAddress;
import net.ramify.model.record.marriage.MarriageRecord;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Standard UK marriage record since 1830s.
 */
public class SpousesAndFathersMarriageRecord implements MarriageRecord, HasAddress {

    private final DateRange date;
    private final PersonalAttributes groom;
    private final PersonalAttributes groomFather;
    private final PersonalAttributes bride;
    private final PersonalAttributes brideFather;
    private final Address address;
    private final Set<PersonId> witnesses;

    public SpousesAndFathersMarriageRecord(
            final DateRange date,
            @Nonnull final PersonalAttributes groom,
            @CheckForNull final PersonalAttributes groomFather,
            @Nonnull final PersonalAttributes bride,
            @CheckForNull final PersonalAttributes brideFather,
            final Address address,
            final Set<PersonId> witnesses) {
        this.date = date;
        this.groom = groom;
        this.groomFather = groomFather;
        this.bride = bride;
        this.brideFather = brideFather;
        this.address = address;
        this.witnesses = witnesses;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public Address address() {
        return address;
    }

    @Nonnull
    @Override
    public Histories histories() {
        final Marriage marriage = this.marriage();
        final Map<PersonId, PersonalEvents> events = new HashMap<>();
        events.put(groom.personId(), this.lifeEvents(groom, marriage));
        events.put(bride.personId(), this.lifeEvents(bride, marriage));
        if (groomFather != null) {
            events.put(groomFather.personId(), this.lifeEvents(groomFather));
        }
        if (brideFather != null) {
            events.put(brideFather.personId(), this.lifeEvents(brideFather));
        }
        return Histories.of(events);
    }

    private PersonalEvents lifeEvents(final PersonalAttributes details) {
        return this.lifeEvents(details, null);
    }

    private PersonalEvents lifeEvents(final PersonalAttributes attributes, final Marriage marriage) {
        if (marriage == null) {
            return attributes.inferredEvents();
        }
        final Set<Event> events = new HashSet<>(attributes.inferredEvents().events());
        events.add(marriage);
        return PersonalEvents.of(attributes.personId(), events);
    }

    @Nonnull
    @Override
    public Family family() {
        final Set<Relationship> relationships = new HashSet<>();
        relationships.add(new MarriedCouple(groom.personId(), bride.personId()));
        if (groomFather != null) {
            relationships.add(new ParentChild(groomFather.personId(), groom.personId()));
        }
        if (brideFather != null) {
            relationships.add(new ParentChild(brideFather.personId(), bride.personId()));
        }
        return Family.of(relationships);
    }

}
