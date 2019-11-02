package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.xml.XmlRelationship;
import net.ramify.model.person.Person;
import net.ramify.model.place.Place;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "familyResidence")
public class XmlFamilyResidenceRecord extends XmlResidenceRecord {

    @XmlElementRef
    private List<XmlRelationship> relationships;

    @Override
    public FamilyBuilder family(final Person person, final Place place, final DateRange date, final RecordContext context) {
        final var builder = super.family(person, place, date, context);
        if (relationships != null) {
            relationships.forEach(r -> r.addRelationship(person, builder, context, date));
        }
        return builder;
    }

}
