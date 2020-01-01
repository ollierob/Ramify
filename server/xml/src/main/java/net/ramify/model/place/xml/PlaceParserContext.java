package net.ramify.model.place.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.provider.PlaceProvider;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class PlaceParserContext extends ParserContext {

    private final CountryIso iso;
    private final PlaceProvider places;

    PlaceParserContext(
            final NameParser nameParser,
            final DateParser dateParser,
            final CountryIso iso,
            final PlaceProvider places) {
        super(nameParser, dateParser);
        this.iso = iso;
        this.places = places;
    }

    public CountryIso countryIso(final boolean includeSubdivision) {
        return includeSubdivision ? iso : iso.withoutSubdivision();
    }

    public PlaceProvider places() {
        return places;
    }

}
