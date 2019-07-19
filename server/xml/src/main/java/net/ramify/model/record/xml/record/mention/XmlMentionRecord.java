package net.ramify.model.record.xml.record.mention;

import com.google.common.collect.Sets;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.event.type.misc.Flourished;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.residence.SinglePersonLifeEventRecord;
import net.ramify.model.record.type.LifeEventRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mention")
public class XmlMentionRecord extends XmlRecord {

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    protected boolean deceased() {
        return Boolean.TRUE.equals(deceased);
    }

    public LifeEventRecord buildRecord(final DateRange parentDate, final RecordContext context) {
        final var recordId = this.recordId();
        final var date = parentDate; //TODO also encode here
        final var person = this.person(date, context);
        return new SinglePersonLifeEventRecord(
                recordId,
                person,
                date);
    }

    protected Person person(final DateRange date, final RecordContext context) {
        final var personId = this.personId();
        final var name = this.name(context.nameParser());
        return new GenericRecordPerson(personId, name, this.gender(), this.events(personId, date), this.notes());
    }

    @OverridingMethodsMustInvokeSuper
    protected Set<? extends Event> events(final PersonId personId, final DateRange date) {
        final var events = Sets.<Event>newHashSet();
        if (this.deceased()) events.add(new GenericDeath(personId, BeforeDate.strictlyBefore(date)));
        else events.add(new Flourished(personId, date));
        return events;
    }

}
