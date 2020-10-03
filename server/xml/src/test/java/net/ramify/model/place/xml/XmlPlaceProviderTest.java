package net.ramify.model.place.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.provider.PlaceGroupProvider;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.Parish;
import net.ramify.model.place.settlement.Village;
import net.ramify.model.place.xml.place.XmlPlaces;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.xml.bind.JAXBContext;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class XmlPlaceProviderTest {

    @Test
    void shouldReadPlaces() throws Exception {

        final var context = JAXBContext.newInstance(XmlPlaces.class);
        final var file = new File(XmlPlaceProviderTest.class.getResource("/xml/data").toURI());
        final var groups = mock(PlaceGroupProvider.class);
        final var parserContext = mock(ParserContext.class, Mockito.RETURNS_MOCKS);
        final var placeProvider = XmlPlaceProvider.readPlacesInCountryRoot(context, file, parserContext);

        assertNotNull(placeProvider.get(new PlaceId(CountryIso.GB, Country.class, "england")));
        assertNotNull(placeProvider.get(new PlaceId(CountryIso.GB, Parish.class, "halifax")));
        assertNotNull(placeProvider.get(new PlaceId(CountryIso.GB, Parish.class, "heptonstall")));

        final var heptonstall = placeProvider.get(new PlaceId(CountryIso.GB, Village.class, "heptonstall"));
        assertNotNull(heptonstall);

        final var church = placeProvider.get(new PlaceId(CountryIso.GB, Church.class, "heptonstall_st-thomas"));
        assertNotNull(church);
        //assertEquals(heptonstall, church.parent());

    }

}