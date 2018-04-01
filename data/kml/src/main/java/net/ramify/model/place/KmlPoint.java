package net.ramify.model.place;

import javax.xml.bind.annotation.XmlElement;

public class KmlPoint implements Position {

    @XmlElement(name = "coordinates")
    private String coordinates;

    @Override
    public double latitude() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public double longitude() {
        throw new UnsupportedOperationException(); //TODO
    }

}
