package net.ramify.model.event.type.birth;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.place.Place;
import net.ramify.model.place.proto.PlaceProto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BirthWithPlaceTest {

    @Test
    void testToProtoBuilder() {

        final var delegate = mock(BirthEvent.class);
        when(delegate.toProtoBuilder()).thenAnswer(i -> EventProto.Event.newBuilder());

        final var place = mock(Place.class);
        when(place.toProto()).thenReturn(PlaceProto.Place.newBuilder().setName("place").build());

        final var birth = new BirthWithPlace(delegate, place);

        final var builder = birth.toProtoBuilder();
        assertTrue(builder.hasPlace());
        assertEquals("place", builder.getPlace().getName());

        final var proto = birth.toProto();
        assertTrue(proto.hasPlace());
        assertEquals("place", proto.getPlace().getName());

    }

    @Test
    void testHandler() {

        final var delegate = mock(BirthEvent.class);
        final var place = mock(Place.class);
        final var birth = new BirthWithPlace(delegate, place);

        final var handler = mock(EventHandler.class);
        verify(handler).handle(birth);

    }

}