package net.ramify.model.record.xml.record.burial;

import com.google.common.collect.Sets;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.burial.Burial;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.burial.ChurchBurial;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.event.type.residence.Residence;
import net.ramify.model.family.Family;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.church.ChurchBurialRecord;
import net.ramify.model.record.type.BurialRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.objects.Functions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Month;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "churchBurial")
public class XmlBurialRecord extends XmlRecord {

    @XmlAttribute(name = "age", required = false)
    private Integer deathAge;

    @XmlAttribute(name = "residence", required = false)
    private String residence;

    @XmlAttribute(name = "year")
    private int year;

    @XmlAttribute(name = "month")
    private int month;

    @XmlAttribute(name = "day")
    private int dayOfMonth;

    @XmlElement(name = "deathDate", required = false, namespace = XmlDateRange.NAMESPACE)
    private XmlExactDate deathDate;

    public BurialRecord build(final PlaceProvider places, final NameParser nameParser, final PlaceId placeId) {
        final var burialDate = ExactDate.on(year, Month.of(month), dayOfMonth);
        return new ChurchBurialRecord(
                this.recordId(),
                burialDate,
                placeId,
                this.family(places, nameParser, burialDate));
    }

    Family family(final PlaceProvider places, final NameParser nameParser, final ExactDate burialDate) {
        final var person = this.person(nameParser, burialDate, places);
        return new SinglePersonFamily(person);
    }

    Person person(final NameParser nameParser, final ExactDate burialDate, final PlaceProvider places) {
        final var personId = this.personId();
        return new GenericRecordPerson(
                personId,
                this.name(nameParser),
                this.gender(),
                this.events(personId, burialDate, places),
                this.notes());
    }

    Set<Event> events(final PersonId personId, final ExactDate burialDate, final PlaceProvider places) {
        final var events = Sets.<Event>newHashSet(this.burial(personId, burialDate));
        if (deathAge != null) events.add(this.birth(personId, burialDate, deathAge)); //FIXME use death date
        if (deathDate != null) events.add(this.death(personId, deathDate.resolve()));
        if (residence != null) events.add(this.residence(personId, burialDate, new PlaceId(residence)));
        return events;
    }

    Birth birth(final PersonId personId, final ExactDate burialDate, final int deathAge) {
        return new GenericBirth(personId, burialDate.yearsAgo(deathAge));
    }

    Residence residence(final PersonId personId, final DateRange burialDate, final PlaceId placeId) {
        return new GenericResidence(personId, BeforeDate.strictlyBefore(burialDate), placeId);
    }

    Death death(final PersonId personId, final ExactDate date) {
        return new GenericDeath(personId, date);
    }

    Burial burial(final PersonId personId, final ExactDate date) {
        return new ChurchBurial(personId, date, Functions.ifNonNull(deathAge, Age::ofYears));
    }

}
