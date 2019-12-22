package net.ramify.model.place.xml.location;

import net.meerkat.collections.list.Lists;
import net.ramify.model.place.position.Area;
import net.ramify.model.place.position.Point;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "placeArea")
public class XmlPositionArea extends XmlPosition {

    @XmlElement(namespace = XmlPlace.NAMESPACE, name = "center", required = true)
    private XmlPoint center;

    @XmlElement(namespace = XmlPlace.NAMESPACE, name = "point", required = true)
    private List<XmlPoint> points;

    @Override
    protected Area toPosition(final int zoom) {
        return new AreaPosition(center.toPoint(), Lists.eagerlyTransform(points, XmlPoint::toPoint), zoom);
    }

    @XmlTransient
    private static class AreaPosition implements Area {

        private final Point center;
        private final List<Point> boundary;
        private final int zoom;

        private AreaPosition(Point center, List<Point> boundary, int zoom) {
            this.center = center;
            this.boundary = boundary;
            this.zoom = zoom;
        }

        @Nonnull
        @Override
        public List<Point> boundaryPoints() {
            return boundary;
        }

        @Nonnull
        @Override
        public Point center() {
            return center;
        }

        @Override
        public int zoom() {
            return zoom;
        }

    }

}
