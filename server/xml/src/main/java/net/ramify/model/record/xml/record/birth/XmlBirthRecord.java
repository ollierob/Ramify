package net.ramify.model.record.xml.record.birth;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.xml.XmlRelationship;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.GenericBirthRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.BirthRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "birthRecord")
public class XmlBirthRecord extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "birthPlace", required = false)
    private String birthPlace;

    @XmlElement(name = "birthDate", required = true, namespace = XmlDateRange.NAMESPACE)
    private XmlExactDate birthDate;

    @XmlElementRef
    private List<XmlRelationship> relationships;

    public BirthRecord build(final PlaceId recordCovers, final RecordContext context, final RecordSet recordSet) {
        final var birthDate = this.birthDate.resolve();
        final var birthPlace = this.birthPlace(context);
        return new GenericBirthRecord(
                this.recordId(),
                recordSet,
                birthDate,
                birthPlace,
                this.family(birthDate, context.onDate(birthDate)));
    }

    Family family(final ExactDate birthDate, final RecordContext context) {
        final var person = this.person(birthDate, context);
        final var builder = new FamilyBuilder().addPerson(person);
        if (relationships != null) relationships.forEach(relationship -> relationship.addRelationship(person, builder, context, birthDate));
        return builder.build();
    }

    @CheckForNull
    @Override
    protected Birth inferBirth(final PersonId personId, final DateRange date, final RecordContext context) {
        return new GenericBirth(personId, date).with(this.birthPlace(context));
    }

    @CheckForNull
    Place birthPlace(final RecordContext context) {
        return birthPlace == null ? null : context.places().require(new PlaceId(birthPlace));
    }

    int numIndividuals() {
        return 1 + (relationships == null ? 0 : relationships.size()); //FIXME count unique
    }

}
