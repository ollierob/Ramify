package net.ramify.model.place.xml;

import net.ramify.model.place.building.Church;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.Parish;
import net.ramify.model.place.settlement.Village;
import net.ramify.model.place.xml.place.XmlPlaces;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class XmlPlaceProviderTest {

    @Test
    void shouldReadPlaces() throws Exception {

        final var context = JAXBContext.newInstance(XmlPlaces.class);
        final var file = new File(XmlPlaceProviderTest.class.getResource("/xml/data").toURI());
        final var placeProvider = XmlPlaceProvider.readPlacesInDirectory(context, file);

        assertNotNull(placeProvider.get(new Spid(Country.class, "england")));
        assertNotNull(placeProvider.get(new Spid(Parish.class, "halifax")));
        assertNotNull(placeProvider.get(new Spid(Parish.class, "heptonstall")));

        final var heptonstall = placeProvider.get(new Spid(Village.class, "heptonstall"));
        assertNotNull(heptonstall);

        final var church = placeProvider.get(new Spid(Church.class, "heptonstall_st-thomas"));
        assertNotNull(church);
        assertEquals(heptonstall, church.parent());

    }

}