package net.ramify.model.place.xml.place;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(namespace = "http://ramify.net/places", name = "places")
@XmlRootElement(name = "places")
public class XmlPlaces {

    @XmlElementRef
    private List<XmlPlace> places;

}
