package net.ramify.model.record.xml.record.burial;

import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.family.Family;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.church.ChurchBurialRecord;
import net.ramify.model.record.type.BurialRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "churchBurial")
public class XmlBurialRecord extends XmlRecord {

    @XmlAttribute(name = "age", required = false)
    private Integer deathAge;

    @XmlAttribute(name = "notes", required = false)
    private String notes;

    @XmlAttribute(name = "residence", required = false)
    private String residence;

    @XmlElement(name = "date", required = true)
    private XmlExactDate burialDate;

    public BurialRecord build(final NameParser nameParser, final PlaceId placeId) {
        return new ChurchBurialRecord(
                this.recordId(),
                burialDate.resolve(),
                placeId,
                this.family(nameParser));
    }

    Family family(final NameParser nameParser) {
        throw new UnsupportedOperationException();
    }

}
