package net.ramify.model.record.xml.record.birth;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.GenericBirthRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.BirthRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "birthRecord")
public class XmlBirthRecord extends XmlPersonOnDateWithFamilyRecord {

    @XmlAttribute(name = "birthPlace", required = false)
    private String birthPlace;

    @XmlElement(name = "birthDate", required = true, namespace = XmlDateRange.NAMESPACE)
    private XmlExactDate birthDate;

    public BirthRecord build(final PlaceId recordSetCovers, final RecordContext context, final RecordSet recordSet) {
        final var birthDate = this.birthDate.resolve();
        final var birthPlace = this.birthPlace().orElse(recordSetCovers);
        return new GenericBirthRecord(
                this.recordId(),
                recordSet,
                birthDate,
                this.family(context.onDate(birthDate), birthDate),
                birthPlace);
    }

    @Override
    protected BirthEvent inferBirth(final PersonId personId, final DateRange date, final RecordContext context) {
        return this.eventBuilder(date)
                .withPlace(this.birthPlace(context))
                .toBirth(personId);
    }

    @CheckForNull
    protected Optional<PlaceId> birthPlace() {
        return Optional.ofNullable(birthPlace).map(PlaceId::new);
    }

    protected Place birthPlace(final RecordContext context) {
        return this.birthPlace().map(context.places()::require).orElse(null);
    }

}
