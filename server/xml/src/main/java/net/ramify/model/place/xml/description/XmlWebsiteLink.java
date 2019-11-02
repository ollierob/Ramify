package net.ramify.model.place.xml.description;

import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "websiteLink")
public class XmlWebsiteLink extends XmlLink {

    @CheckForNull
    @Override
    public String text() {
        return "Website";
    }

}
