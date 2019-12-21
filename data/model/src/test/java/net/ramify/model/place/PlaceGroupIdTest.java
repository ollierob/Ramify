package net.ramify.model.place;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceGroupIdTest {

    @Test
    void testWithPlaceType() {

        final var groupId = new PlaceGroupId("GB:heptonstall");
        final var placeId = groupId.withPlaceType("village");

        assertEquals("GB:heptonstall:village", placeId.value());
        assertEquals(new PlaceId("GB:heptonstall:village"), placeId);

    }

}