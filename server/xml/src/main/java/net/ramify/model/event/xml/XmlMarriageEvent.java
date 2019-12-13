package net.ramify.model.event.xml;

import net.ramify.model.event.Event;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.Married;
import net.ramify.utils.collections.SetUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "marriage")
public class XmlMarriageEvent extends XmlLifeEvent {

    @XmlAttribute(name = "spouse", required = true)
    private String spouseId;

    @Override
    public Event toEvent(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(context).toMarriage(personId);
    }

    @Override
    public Set<Relationship> inferredRelationships(final HasPersonId personId) {
        return SetUtils.with(super.inferredRelationships(personId), new Married(personId, this.spouseId()));
    }

    PersonId spouseId() {
        return new PersonId(spouseId);
    }

}
