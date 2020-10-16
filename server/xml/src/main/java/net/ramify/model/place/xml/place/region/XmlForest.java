package net.ramify.model.place.xml.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.Forest;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "forest")
public class XmlForest extends XmlRegion<Forest> {

    XmlForest() {
        super(Forest.class);
    }

    @Override
    protected Forest toPlace(
            final PlaceGroupId groupId,
            final PlaceHistory history,
            final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Forest(this.placeId(context), this.name(), groupId, history);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return Collections.emptyList();
    }

}
