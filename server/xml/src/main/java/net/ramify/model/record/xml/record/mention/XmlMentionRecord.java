package net.ramify.model.record.xml.record.mention;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.event.type.misc.Flourished;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.GenericLifeEventRecord;
import net.ramify.model.record.type.LifeEventRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mention")
public class XmlMentionRecord extends XmlPersonRecord {

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    protected boolean deceased() {
        return Boolean.TRUE.equals(deceased);
    }

    public LifeEventRecord buildRecord(final DateRange parentDate, final RecordContext context, final RecordSet recordSet) {
        final var recordId = this.recordId();
        final var date = parentDate; //TODO also encode here
        final var family = new SinglePersonFamily(this.person(date, context));
        return new GenericLifeEventRecord(
                recordId,
                recordSet,
                family,
                date);
    }

    @Override
    protected Set<Event> events(final PersonId personId, final DateRange date) {
        final var events = super.events(personId, date);
        if (this.deceased()) events.add(new GenericDeath(personId, BeforeDate.strictlyBefore(date)));
        else events.add(new Flourished(personId, date));
        return events;
    }

}
