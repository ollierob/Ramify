package net.ramify.model.place.xml.place.uk;

import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({XmlCountry.class})
public abstract class XmlUkPlace extends XmlPlace {

    public static final String NAMESPACE = XmlPlace.NAMESPACE + "/gb";

    public static final CountryIso GB_ENG = CountrySubdivisionIso.valueOf("GB-ENG");

}
