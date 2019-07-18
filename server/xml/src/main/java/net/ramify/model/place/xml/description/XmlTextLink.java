package net.ramify.model.place.xml.description;

import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "textLink")
public class XmlTextLink extends XmlLink {

    @XmlAttribute(name = "text", required = true)
    private String text;

    @CheckForNull
    @Override
    public String text() {
        return text;
    }

}
