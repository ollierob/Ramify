package net.ramify.model.kml;

import net.ramify.model.place.KmlPlacemark;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({KmlPlacemark.class})
public abstract class KmlDocumentElement {

    protected KmlDocumentElement() {
    }

}
