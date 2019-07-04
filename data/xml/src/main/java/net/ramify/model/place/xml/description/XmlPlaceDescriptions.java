package net.ramify.model.place.xml.description;

import com.google.common.collect.Maps;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "placeDescriptions")
@XmlRootElement(name = "placeDescriptions")
public class XmlPlaceDescriptions {

    @XmlElementRef
    private List<XmlPlaceDescription> descriptions;

    public Map<PlaceId, String> descriptions() {
        if (descriptions == null) return Collections.emptyMap();
        final var map = Maps.<PlaceId, String>newHashMap();
        descriptions.forEach(d -> map.put(d.placeId(), d.description()));
        return map;
    }

}
