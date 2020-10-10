package net.ramify.model.place;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceGroupIdTest {

    @Test
    void testWithPlaceType() {

        final var groupId = new PlaceGroupId("GB-ENG:heptonstall");
        final var placeId = groupId.withPlaceType("village");

        assertEquals("GB-ENG:heptonstall:village", placeId.value());
        assertEquals(new PlaceId("GB-ENG:heptonstall:village"), placeId);
        assertEquals("heptonstall", groupId.name());
        assertEquals("GB-ENG", groupId.countryIso().value());

    }

}