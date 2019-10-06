package net.ramify.model.family.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.record.XmlPersonRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "person")
public class XmlFamilyPerson extends XmlPersonRecord {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "father")
    private String father;

    @XmlAttribute(name = "mother")
    private String mother;

    @Override
    protected PersonId personId() {
        return new PersonId(id);
    }

    protected void family(final ParserContext context, final FamilyBuilder builder) {
        throw new UnsupportedOperationException();
    }

}
