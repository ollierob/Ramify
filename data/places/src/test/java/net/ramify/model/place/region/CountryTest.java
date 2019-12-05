package net.ramify.model.place.region;

import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.iso.CountryIso;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CountryTest {

    @Test
    void testAddress() {
        assertEquals("England", new Country(mock(Spid.class), "England", mock(CountryIso.class)).address());
    }

}