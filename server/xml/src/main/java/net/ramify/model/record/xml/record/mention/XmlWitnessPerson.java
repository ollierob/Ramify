package net.ramify.model.record.xml.record.mention;

import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "witness")
public class XmlWitnessPerson extends XmlPersonRecord {

    @XmlAttribute(name = "signed")
    private XmlSignature signature;

    @Override
    protected MutablePersonEventSet events(final PersonId personId, final RecordContext context) {
        final var events = super.events(personId, context);
        events.add(this.mention(personId, context));
        return events;
    }

    protected LifeEvent mention(final PersonId personId, final RecordContext context) {
        return EventBuilder.builderWithRandomId(context.recordDate()).toFlourished(personId);
    }

}
