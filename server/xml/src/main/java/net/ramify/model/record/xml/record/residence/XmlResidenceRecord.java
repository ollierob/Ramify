package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.residence.SinglePersonResidenceRecord;
import net.ramify.model.record.type.ResidenceRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.objects.Functions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "residence")
public class XmlResidenceRecord extends XmlRecord {

    @XmlAttribute(name = "placeId", required = false)
    private String placeId;

    public ResidenceRecord build(final Place parentPlace, final DateRange date, final RecordContext context) {
        final var recordId = this.recordId();
        final var place = Functions.ifNonNull(placeId, id -> context.places().require(new PlaceId(id)), parentPlace);
        final var person = this.person(place, date, context);
        return new SinglePersonResidenceRecord(recordId, place, person, date);
    }

    Person person(final Place place, final DateRange date, final RecordContext context) {
        final var id = this.personId();
        final var name = this.name(context.nameParser());
        final var gender = this.gender();
        final var events = this.events(id, place, date);
        final var notes = this.notes();
        return new GenericRecordPerson(id, name, gender, events, notes);
    }

    Set<Event> events(final PersonId personId, final Place place, final DateRange date) {
        return Collections.singleton(new GenericResidence(personId, date, place));
    }

}
