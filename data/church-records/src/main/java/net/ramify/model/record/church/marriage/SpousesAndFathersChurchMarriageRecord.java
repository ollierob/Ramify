package net.ramify.model.record.church.marriage;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.family.Family;
import net.ramify.model.family.relationship.MarriedCouple;
import net.ramify.model.family.relationship.ParentChild;
import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonalDetails;
import net.ramify.model.person.event.Marriage;
import net.ramify.model.person.event.PersonalEvents;
import net.ramify.model.place.address.Address;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpousesAndFathersChurchMarriageRecord implements ChurchMarriageRecord {

    private final DateRange date;
    private final PersonalDetails groom;
    private final PersonalDetails groomFather;
    private final PersonalDetails bride;
    private final PersonalDetails brideFather;
    private final Address address;

    public SpousesAndFathersChurchMarriageRecord(
            final DateRange date,
            @Nonnull final PersonalDetails groom,
            @CheckForNull final PersonalDetails groomFather,
            @Nonnull final PersonalDetails bride,
            @CheckForNull final PersonalDetails brideFather,
            final Address address) {
        this.date = date;
        this.groom = groom;
        this.groomFather = groomFather;
        this.bride = bride;
        this.brideFather = brideFather;
        this.address = address;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public Address churchAddress() {
        return address;
    }

    @Nonnull
    @Override
    public Map<Person, PersonalEvents> personalEvents() {
        final Marriage marriage = this.marriage();
        final Map<Person, PersonalEvents> events = new HashMap<>();
        events.put(groom.person(), this.lifeEvents(groom, marriage));
        events.put(bride.person(), this.lifeEvents(bride, marriage));
        if (groomFather != null) {
            events.put(groomFather.person(), this.lifeEvents(groomFather));
        }
        if (brideFather != null) {
            events.put(brideFather.person(), this.lifeEvents(brideFather));
        }
        return events;
    }

    Marriage marriage() {
        return new Marriage(date, "Marriage", address);
    }

    PersonalEvents lifeEvents(final PersonalDetails details) {
        return this.lifeEvents(details, null);
    }

    PersonalEvents lifeEvents(final PersonalDetails details, final Marriage marriage) {
        final Set<Event> events = new HashSet<>(details.inferredEventSet(date));
        if (marriage != null) {
            events.add(marriage);
        }
        return PersonalEvents.of(events);
    }

    @Nonnull
    @Override
    public Family family() {
        final Set<Relationship> relationships = new HashSet<>();
        relationships.add(new MarriedCouple(groom.person(), bride.person()));
        if (groomFather != null) {
            relationships.add(new ParentChild(groomFather.person(), groom.person()));
        }
        if (brideFather != null) {
            relationships.add(new ParentChild(brideFather.person(), bride.person()));
        }
        return Family.of(relationships);
    }

}
