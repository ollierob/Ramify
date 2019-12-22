package net.ramify.model.place.xml.location;

import net.ramify.model.place.position.Point;
import net.ramify.model.place.position.Position;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "placePoint")
public class XmlPositionPoint extends XmlPosition {

    @XmlElement(namespace = XmlPlace.NAMESPACE, name = "point", required = true)
    private XmlPoint point;

    @Override
    protected Position toPosition(int zoom) {
        return new PointPosition(point.toPoint(), zoom);
    }

    @XmlTransient
    private static class PointPosition implements Position {

        private final Point point;
        private final int zoom;

        private PointPosition(final Point point, final int zoom) {
            this.point = point;
            this.zoom = zoom;
        }

        @Nonnull
        @Override
        public Point center() {
            return point;
        }

        @Nonnull
        @Override
        public List<Point> boundaryPoints() {
            return Collections.emptyList();
        }

        @Override
        public int zoom() {
            return zoom;
        }

    }

}
