package net.ramify.model.event.type.civil;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.birth.GenericBirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class GenericBirthEventTest {

    @Test
    void testWithPlace() {

        final var birth = new GenericBirthEvent(mock(PersonId.class), mock(DateRange.class));
        final var place = mock(Place.class);

        //When
        final var birthWithPlace = birth.with(place);

        //Then
        assertTrue(birthWithPlace.isBirth());
        assertTrue(birthWithPlace instanceof HasPlaceId);

    }

}