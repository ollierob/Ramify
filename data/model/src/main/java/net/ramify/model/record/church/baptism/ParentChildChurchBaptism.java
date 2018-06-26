package net.ramify.model.record.church.baptism;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Baptism;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.family.Family;
import net.ramify.model.family.relationship.ParentChild;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonalDetails;
import net.ramify.model.place.address.Address;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ParentChildChurchBaptism implements ChurchBaptismRecord {

    private final Address address;
    private final PersonalDetails parent;
    private final PersonalDetails child;
    private final DateRange date;

    public ParentChildChurchBaptism(
            final Address address,
            final PersonalDetails parent,
            final PersonalDetails child,
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
    public Map<Person, PersonalEvents> personalEvents() {
        final Map<Person, PersonalEvents> events = new HashMap<>();
        events.put(parent.person(), PersonalEvents.of(parent.inferredEventSet()));
        events.put(child.person(), PersonalEvents.of(this.baptism(), child.inferredEventSet()));
        return events;
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
