package net.ramify.model.record.xml.record;

import com.google.common.collect.Sets;
import net.meerkat.functions.consumers.Consumers;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.xml.XmlAge;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public class XmlPersonOnDateRecord extends XmlPersonRecord {

    public static final String NAMESPACE = "http://ramify.net/records";

    @XmlAttribute(name = "age")
    private Integer simpleAge;

    @XmlElementRef(required = false)
    private XmlAge complexAge;

    protected Person person(final DateRange date, final RecordContext context) {
        return this.person(context, id -> this.events(id, date, context));
    }

    protected Person person(final RecordContext context, final Function<PersonId, Set<? extends Event>> createEvents) {
        final var personId = this.personId();
        final var name = this.name(context.nameParser());
        final var gender = this.gender();
        final var events = createEvents.apply(personId);
        final var notes = this.notes();
        return new GenericRecordPerson(personId, name, gender, events, notes);
    }

    @OverridingMethodsMustInvokeSuper
    protected Set<Event> events(final PersonId personId, final DateRange date, final RecordContext context) {
        final var events = Sets.<Event>newHashSet();
        events.addAll(this.events(personId, context));
        //FIXME only infer birth if not already added
        Consumers.ifNonNull(this.inferBirth(personId, date, context), events::add);
        return events;
    }

    protected RecordId recordId() {
        return new RecordId(UUID.randomUUID().toString()); //FIXME
    }

    @CheckForNull
    protected Age age() {
        if (complexAge != null) return complexAge.age();
        return Functions.ifNonNull(simpleAge, Age::ofYears);
    }

    @CheckForNull
    protected BirthEvent inferBirth(final PersonId personId, final DateRange date, final RecordContext context) {
        final var age = this.age();
        if (age == null) return null;
        return this.eventBuilder(age.birthDate(date)).toBirth(personId);
    }

    protected EventBuilder eventBuilder(final DateRange date) {
        return EventBuilder.builderWithRandomId(date)
                .withGivenAge(this.age());
    }

}
