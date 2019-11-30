package net.ramify.model.place.xml.place.usa;

import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({XmlCountry.class})
public abstract class XmlUsaPlace extends XmlPlace {

    public static final String NAMESPACE = XmlPlace.NAMESPACE + "/us";

}
