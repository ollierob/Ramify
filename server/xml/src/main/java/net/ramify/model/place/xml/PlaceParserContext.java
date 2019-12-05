package net.ramify.model.place.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceGroupProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.region.iso.CountryIso;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Optional;

@XmlTransient
public class PlaceParserContext extends ParserContext {

    private final CountryIso iso;
    private final PlaceProvider places;
    private final PlaceGroupProvider groups;

    PlaceParserContext(
            final NameParser nameParser,
            final DateParser dateParser,
            final CountryIso iso,
            final PlaceProvider places,
            final PlaceGroupProvider groups) {
        super(nameParser, dateParser);
        this.iso = iso.withoutSubdivision();
        this.places = places;
        this.groups = groups;
    }

    public CountryIso countryIso() {
        return iso;
    }

    public PlaceProvider places() {
        return places;
    }

    public Optional<PlaceGroup> group(final PlaceId placeId) {
        return Optional.ofNullable(groups.getGroup(placeId));
    }

}
