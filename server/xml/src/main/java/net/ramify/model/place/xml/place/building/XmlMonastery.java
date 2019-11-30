package net.ramify.model.place.xml.place.building;

import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Monastery;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "monastery")
public class XmlMonastery extends XmlBuilding<Monastery> {

    XmlMonastery() {
        super(Monastery.class);
    }

    @Override
    protected Monastery place(final Place parent, final PlaceGroupId groupId, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Monastery(this.placeId(), this.name(), parent, groupId, this.history(context));
    }

}
