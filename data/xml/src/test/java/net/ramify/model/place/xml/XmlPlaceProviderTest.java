package net.ramify.model.place.xml;

import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.xml.place.XmlPlaces;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class XmlPlaceProviderTest {

    @Test
    void shouldReadPlaces() throws Exception {

        final var context = JAXBContext.newInstance(XmlPlaces.class);
        final var file = new File(XmlPlaceProviderTest.class.getResource("/xml/data").toURI());
        final var placeProvider = XmlPlaceProvider.readPlacesInDirectory(context, file);

        assertNotNull(placeProvider.require(new Spid(Country.class, "england")));

    }

}