package net.ramify.model.place.xml.location;

import com.google.common.collect.Maps;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.position.Position;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "placePositions")
@XmlRootElement(name = "placePositions")
public class XmlPositions {

    @XmlElementRef
    private List<XmlPosition> positions;

    @Nonnull
    public Map<PlaceId, Position> positions() {
        if (positions == null) return Collections.emptyMap();
        final var map = Maps.<PlaceId, Position>newHashMapWithExpectedSize(positions.size());
        positions.forEach(p -> map.put(p.placeId(), p.toPosition()));
        return map;
    }

}
