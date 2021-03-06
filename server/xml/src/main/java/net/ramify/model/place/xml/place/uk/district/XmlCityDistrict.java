package net.ramify.model.place.xml.place.uk.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.district.CityDistrict;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlRegion;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "cityDistrict")
public class XmlCityDistrict extends XmlRegion<CityDistrict> {

    XmlCityDistrict() {
        super(CityDistrict.class);
    }

    @Override
    protected CityDistrict toPlace(
            final PlaceGroupId groupId,
            final PlaceHistory history,
            final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new CityDistrict(this.placeId(context), this.name(), groupId, history);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return Collections.emptyList();
    }

}

