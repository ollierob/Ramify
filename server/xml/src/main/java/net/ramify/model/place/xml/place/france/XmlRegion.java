package net.ramify.model.place.xml.place.france;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.CountryRegion;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlFrance.NAMESPACE, name = "region")
public class XmlRegion extends net.ramify.model.place.xml.place.region.XmlRegion<Region> {

    @XmlElementRef
    private List<XmlDepartment> departments;

    XmlRegion() {
        super(Region.class);
    }

    @Override
    protected Region toPlace(final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new CountryRegion(this.placeId(context), this.name(), groupId, history);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return MoreObjects.firstNonNull(departments, Collections.emptyList());
    }

}
