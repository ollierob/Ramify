package net.ramify.model.place.xml.location;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.position.Point;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.utils.collections.MapUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "placeLocations")
@XmlRootElement(name = "placeLocations")
public class XmlPlaceLocations {

    @XmlElementRef
    private List<XmlPlacePoint> placePoints;

    @Nonnull
    public Map<PlaceId, Point> points() {
        if (placePoints == null) return Collections.emptyMap();
        return MapUtils.toMapLastKey(placePoints, XmlPlacePoint::placeId);
    }

}
