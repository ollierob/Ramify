package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.GenericResidenceRecord;
import net.ramify.model.record.type.LifeEventRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.objects.Functions;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "residence")
public class XmlResidenceRecord extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "placeId")
    private String placeId;

    @Nonnull
    public LifeEventRecord build(final Place parentPlace, final DateRange date, final RecordContext context, final RecordSet recordSet) {
        final var recordId = this.recordId();
        try {
            final var place = Functions.ifNonNull(placeId, id -> context.places().require(new PlaceId(id)), parentPlace);
            final var person = this.person(place, date, context);
            final var family = this.family(person, place, date, context).build();
            return new GenericResidenceRecord(recordId, recordSet, family, date, place);
        } catch (final Exception ex) {
            throw new RuntimeException("Error building residence record for " + recordId, ex);
        }
    }

    public Person person(final Place place, final DateRange date, final RecordContext context) {
        final var id = this.personId();
        final var name = this.name(context.nameParser());
        final var gender = this.gender();
        final var events = this.events(id, place, date, context);
        final var notes = this.notes();
        return new GenericRecordPerson(id, name, gender, events, notes);
    }

    protected MutablePersonEventSet events(final PersonId personId, final Place place, final DateRange date, final RecordContext context) {
        final var events = super.events(personId, date, context);
        events.add(this.eventBuilder(date).withPlace(place).toResidence(personId));
        return events;
    }

    public FamilyBuilder family(final Person person, final Place place, final DateRange date, final RecordContext context) {
        return new FamilyBuilder(person);
    }

}
