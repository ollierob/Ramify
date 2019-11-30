package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({XmlCity.class, XmlTown.class})
public abstract class XmlSettlement extends XmlPlace {

}
