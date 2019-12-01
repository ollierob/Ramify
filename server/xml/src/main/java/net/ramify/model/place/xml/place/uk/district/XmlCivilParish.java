package net.ramify.model.place.xml.place.uk.district;

import com.google.common.base.MoreObjects;
import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.region.district.CivilParish;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.settlement.XmlTown;
import net.ramify.model.place.xml.place.settlement.XmlVillage;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "civilParish")
public class XmlCivilParish extends XmlArea<CivilParish> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlTown.class),
            @XmlElementRef(type = XmlVillage.class),
    })
    private List<XmlPlace> children;

    XmlCivilParish() {
        super(CivilParish.class);
    }

    @Override
    protected CivilParish place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new CivilParish(this.placeId(), this.name(), parent, groupId, history);
    }

    @Override
    protected PlaceHistory history(ParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), CivilParish.HISTORY);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return children;
    }

}