package net.ramify.model.family.xml;

import javax.xml.bind.annotation.XmlElementRef;
import java.util.List;

public class XmlFamily {

    public static final String NAMESPACE = "http://ramify.net/families";

    @XmlElementRef
    private List<XmlFamilyPerson> people;

}
