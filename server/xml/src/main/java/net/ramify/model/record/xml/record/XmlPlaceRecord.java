package net.ramify.model.record.xml.record;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;

public abstract class XmlPlaceRecord extends XmlRecord {

    @XmlAttribute(name = "place", required = false)
    private String placeId;

    @CheckForNull
    protected Place place(final PlaceProvider placeProvider) {
        return Functions.ifNonNull(placeId, id -> placeProvider.require(new PlaceId(id)));
    }

}
