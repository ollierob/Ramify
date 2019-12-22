package net.ramify.model.event.xml.person;

import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "any")
public class XmlAnyLifeEvent extends XmlLifeEvent {

    @XmlAttribute(name = "title", required = true)
    private String title;

    @Override
    public PersonEvent build(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(context).toAnyLifeEvent(personId, title);
    }

}
