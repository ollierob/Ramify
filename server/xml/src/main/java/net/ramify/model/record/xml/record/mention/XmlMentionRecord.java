package net.ramify.model.record.xml.record.mention;

import com.google.common.collect.Sets;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.event.type.misc.Flourished;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mention")
public class XmlMentionRecord extends XmlRecord {

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    protected boolean deceased() {
        return Boolean.TRUE.equals(deceased);
    }

    @OverridingMethodsMustInvokeSuper
    protected Collection<? extends Event> events(final PersonId personId, final DateRange date) {
        final var events = Sets.<Event>newHashSet();
        if (this.deceased()) events.add(new GenericDeath(personId, BeforeDate.strictlyBefore(date)));
        else events.add(new Flourished(personId, date));
        return events;
    }

}
