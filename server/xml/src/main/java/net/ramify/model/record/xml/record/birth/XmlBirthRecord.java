package net.ramify.model.record.xml.record.birth;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.birth.GenericBirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.GenericBirthRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.BirthRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "birthRecord")
public class XmlBirthRecord extends XmlPersonOnDateWithFamilyRecord {

    @XmlAttribute(name = "birthPlace", required = false)
    private String birthPlace;

    @XmlElement(name = "birthDate", required = true, namespace = XmlDateRange.NAMESPACE)
    private XmlExactDate birthDate;

    public BirthRecord build(final PlaceId recordCovers, final RecordContext context, final RecordSet recordSet) {
        final var birthDate = this.birthDate.resolve();
        final var birthPlace = this.birthPlace();
        return new GenericBirthRecord(
                this.recordId(),
                recordSet,
                birthDate,
                birthPlace,
                this.family(context.onDate(birthDate), birthDate));
    }

    @Override
    protected BirthEvent inferBirth(final PersonId personId, final DateRange date, final RecordContext context) {
        return new GenericBirthEvent(personId, date).with(this.birthPlace(context));
    }

    @CheckForNull
    PlaceId birthPlace() {
        return birthPlace == null ? null : new PlaceId(birthPlace);
    }

    Place birthPlace(final RecordContext context) {
        return Functions.ifNonNull(this.birthPlace(), id -> context.places().require(id));
    }

}
