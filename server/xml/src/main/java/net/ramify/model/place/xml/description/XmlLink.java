package net.ramify.model.place.xml.description;

import net.ramify.model.util.Link;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({XmlTextLink.class, XmlWikipediaLink.class})
public abstract class XmlLink implements Link {

    @XmlAttribute(name = "href", required = true)
    private String href;

    @Nonnull
    @Override
    public String href() {
        return href;
    }

}
