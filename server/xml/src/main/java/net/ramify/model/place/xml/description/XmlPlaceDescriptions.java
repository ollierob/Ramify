package net.ramify.model.place.xml.description;

import com.google.common.collect.Collections2;
import net.ramify.model.place.PlaceDescription;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "placeDescriptions")
@XmlRootElement(name = "placeDescriptions")
public class XmlPlaceDescriptions {

    @XmlElementRef
    private List<XmlPlaceDescription> descriptions;

    @Nonnull
    public Collection<PlaceDescription> descriptions(final PlaceProvider placeProvider) {
        if (descriptions == null) return Collections.emptySet();
        return Collections2.transform(descriptions, d -> d.description(placeProvider));
    }

}
