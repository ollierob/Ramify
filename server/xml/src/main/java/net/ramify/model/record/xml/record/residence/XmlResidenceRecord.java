package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.Place;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.residence.SinglePersonResidenceRecord;
import net.ramify.model.record.type.ResidenceRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "residence")
public class XmlResidenceRecord extends XmlRecord {

    public ResidenceRecord build(final NameParser nameParser, final Place place, final DateRange date) {
        final var id = this.recordId();
        final var person = this.person(nameParser, place, date);
        return new SinglePersonResidenceRecord(id, place, person, date);
    }

    Person person(final NameParser nameParser, final Place place, final DateRange date) {
        final var id = this.personId();
        final var name = this.name(nameParser);
        final var gender = this.gender();
        final var events = this.events(id, place, date);
        final var notes = this.notes();
        return new GenericRecordPerson(id, name, gender, events, notes);
    }

    Set<Event> events(final PersonId personId, final Place place, final DateRange date) {
        return Collections.singleton(new GenericResidence(personId, date, place));
    }

}
