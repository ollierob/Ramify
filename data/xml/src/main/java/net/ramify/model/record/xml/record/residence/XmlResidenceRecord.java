package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.person.Person;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.residence.SinglePersonResidenceRecord;
import net.ramify.model.record.type.ResidenceRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "residence")
public class XmlResidenceRecord extends XmlRecord {

    @XmlAttribute(name = "firstLastName", required = false)
    private String firstLastName;

    ResidenceRecord build(final NameParser nameParser, final PlaceId placeId, final DateRange date) {
        final var id = this.recordId();
        final var person = this.person(nameParser);
        return new SinglePersonResidenceRecord(id, placeId, person, date);
    }

    Person person(final NameParser nameParser) {
        throw new UnsupportedOperationException();
    }

}
