package net.ramify.model.record.church.baptism;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Baptism;
import net.ramify.model.event.Histories;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.family.Family;
import net.ramify.model.family.relationship.ParentChild;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonalAttributes;
import net.ramify.model.place.address.Address;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ParentChildChurchBaptism implements ChurchBaptismRecord {

    private final Address address;
    private final PersonalAttributes parent;
    private final PersonalAttributes child;
    private final DateRange date;

    public ParentChildChurchBaptism(
            final Address address,
            final PersonalAttributes parent,
            final PersonalAttributes child,
            final DateRange date) {
        this.address = address;
        this.parent = parent;
        this.child = child;
        this.date = date;
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
        final Map<Person, PersonalEvents> events = new HashMap<>();
        events.put(parent.person(), parent.inferredEvents());
        events.put(child.person(), child.inferredEvents().and(this.baptism()));
        return Histories.of(events);
    }

    @Override
    public Baptism baptism() {
        return new Baptism(date, address);
    }

    @Nonnull
    @Override
    public Family family() {
        return Family.of(this.relationship());
    }

    protected ParentChild relationship() {
        return new ParentChild(parent.person(), child.person());
    }

}
