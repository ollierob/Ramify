package net.ramify.model.place;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceIdTest {

    @Test
    void testPlaceGroupId() {

        final var placeId = new PlaceId("GB:heptonstall:village");
        final var groupId = placeId.placeGroupId();

        assertEquals("GB:heptonstall", groupId.value());
        assertEquals(new PlaceGroupId("GB:heptonstall"), groupId);

    }

}