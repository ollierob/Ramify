package net.ramify.model.record.xml.record;

import com.google.common.collect.Sets;
import net.ramify.model.ParserContext;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.xml.XmlAge;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.RecordId;
import net.ramify.utils.objects.Consumers;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import java.time.Period;
import java.util.Set;
import java.util.UUID;

public class XmlPersonOnDateRecord extends XmlPersonRecord {

    public static final String NAMESPACE = "http://ramify.net/records";

    @XmlAttribute(name = "age")
    private Integer age;

    @XmlElementRef(required = false)
    private XmlAge complexAge;

    protected Person person(final DateRange date, final ParserContext context) {
        final var id = this.personId();
        final var name = this.name(context.nameParser());
        final var gender = this.gender();
        final var events = this.events(id, date);
        final var notes = this.notes();
        return new GenericRecordPerson(id, name, gender, events, notes);
    }

    @OverridingMethodsMustInvokeSuper
    protected Set<Event> events(final PersonId personId, final DateRange date) {
        final var events = Sets.<Event>newHashSet();
        Consumers.ifNonNull(this.birth(personId, date), events::add);
        return events;
    }

    protected RecordId recordId() {
        return new RecordId(UUID.randomUUID().toString()); //FIXME
    }

    @CheckForNull
    protected Period age() {
        if (complexAge != null) return complexAge.age();
        return Functions.ifNonNull(age, Period::ofYears);
    }

    @CheckForNull
    protected Birth birth(final PersonId personId, final DateRange date) {
        if (age == null) return null;
        return new GenericBirth(personId, Age.birthDate(age, date));
    }

}