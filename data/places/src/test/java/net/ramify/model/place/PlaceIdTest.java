package net.ramify.model.place;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceIdTest {

    @Test
    void testName() {

        final var placeId = new PlaceId("GB-ENG:heptonstall:village");

        assertEquals("GB-ENG", placeId.countryIso().value());
        assertEquals("heptonstall", placeId.name());

    }

    @Test
    void testPlaceGroupId() {

        final var placeId = new PlaceId("GB-ENG:heptonstall:village");
        final var groupId = placeId.placeGroupId();

        assertEquals("GB-ENG:heptonstall", groupId.value());
        assertEquals(new PlaceGroupId("GB-ENG:heptonstall"), groupId);

    }

}