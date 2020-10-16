package net.ramify.model.place;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaceIdTest {

    public static PlaceId mockPlaceId() {
        final var mock = mock(PlaceId.class);
        when(mock.placeId()).thenReturn(mock);
        return mock;
    }

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