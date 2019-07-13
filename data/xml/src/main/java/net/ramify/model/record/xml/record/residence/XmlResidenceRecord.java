package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.person.xml.XmlGender;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.residence.SinglePersonResidenceRecord;
import net.ramify.model.record.type.ResidenceRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "residence")
public class XmlResidenceRecord extends XmlRecord {

    @XmlAttribute(name = "gender", required = true)
    private XmlGender gender;

    @XmlAttribute(name = "notes", required = false)
    private String notes;

    ResidenceRecord build(final NameParser nameParser, final PlaceId placeId, final DateRange date) {
        final var id = this.recordId();
        final var person = this.person(nameParser, placeId, date);
        return new SinglePersonResidenceRecord(id, placeId, person, date);
    }

    Person person(final NameParser nameParser, final PlaceId placeId, final DateRange date) {
        final var id = this.personId();
        final var name = this.name(nameParser);
        final var gender = this.gender.gender();
        final var events = this.events(id, placeId, date);
        return new GenericRecordPerson(id, name, gender, events, notes);
    }

    PersonId personId() {
        return new PersonId(UUID.randomUUID().toString());
    }

    Set<Event> events(final PersonId personId, final PlaceId placeId, final DateRange date) {
        return Collections.singleton(new GenericResidence(personId, date, placeId));
    }

}
