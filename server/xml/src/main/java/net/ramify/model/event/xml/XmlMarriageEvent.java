package net.ramify.model.event.xml;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.marriage.GenericMarriage;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "marriage")
public class XmlMarriageEvent extends XmlLifeEvent {

    @XmlAttribute(name = "spouse")
    private String spouseId;

    @Override
    public Event toEvent(final PersonId personId, final RecordContext context) {
        return new GenericMarriage(
                personId,
                this.date(context),
                this.age()).with(this.place(context));
    }

}
