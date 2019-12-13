package net.ramify.model.record.xml.record.birth;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.birth.Baptism;
import net.ramify.model.event.type.birth.GenericBaptism;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.record.church.ChurchBaptismRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.BaptismRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "baptism")
public class XmlBaptismRecord extends XmlPersonOnDateWithFamilyRecord {

    @XmlElement(name = "date", required = true, namespace = XmlDateRange.NAMESPACE)
    private XmlExactDate date;

    ExactDate date() {
        return date.resolve();
    }

    public BaptismRecord build(final Place church, final RecordContext context, final RecordSet recordSet) {
        final var date = this.date();
        return new ChurchBaptismRecord(
                this.recordId(),
                recordSet,
                date,
                church.placeId(),
                this.family(context, date, id -> this.events(id, date, church)));
    }

    Set<? extends Event> events(final PersonId id, final DateRange date, final Place church) {
        return Collections.singleton(this.baptism(id, date, church));
    }

    Baptism baptism(final PersonId personId, final DateRange date, final Place church) {
        return new GenericBaptism(this.randomEventId(), personId, date).with(church);
    }

}
