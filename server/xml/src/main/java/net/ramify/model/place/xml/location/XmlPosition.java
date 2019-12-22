package net.ramify.model.place.xml.location;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.position.Position;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "position")
@XmlSeeAlso({XmlPositionPoint.class, XmlPositionArea.class})
public abstract class XmlPosition {

    @XmlAttribute(name = "id", required = true)
    private String placeId;

    @XmlAttribute(name = "zoom")
    private Integer zoom;

    protected PlaceId placeId() {
        return new PlaceId(placeId);
    }

    public Position toPosition() {
        return this.toPosition(MoreObjects.firstNonNull(zoom, 0));
    }

    protected abstract Position toPosition(int zoom);

}
