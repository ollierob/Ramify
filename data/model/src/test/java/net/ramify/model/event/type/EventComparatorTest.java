package net.ramify.model.event.type;

import com.google.common.collect.ImmutableList;
import net.ramify.model.event.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventComparatorTest {

    @Test
    void testCompareDeath() {

        final var death = mockDeath();
        final var will = mockPostDeath();

        assertEquals(0, EventComparator.compareDeath(death, death));
        assertEquals(-1, EventComparator.compareDeath(death, will));
        assertEquals(1, EventComparator.compareDeath(will, death));

    }

    @Test
    void shouldSortDefault() {

        final var birth = mockBirth();
        final var life = mockLife();
        final var death = mockDeath();
        final var will = mockPostDeath();

        final var list = ImmutableList.sortedCopyOf(EventComparator.DEFAULT, ImmutableList.of(death, will, life, birth));
        assertEquals(ImmutableList.of(birth, life, death, will), list);

    }

    static Event mockEvent() {
        return mock(Event.class);
    }

    static Event mockBirth() {
        final var mock = mockEvent();
        when(mock.isBirth()).thenReturn(true);
        return mock;
    }

    static Event mockLife() {
        final var mock = mockEvent();
        when(mock.isLife()).thenReturn(true);
        return mock;
    }

    static Event mockDeath() {
        final var mock = mockEvent();
        when(mock.isDeath()).thenReturn(true);
        return mock;
    }

    static Event mockPostDeath() {
        final var mock = mockEvent();
        when(mock.isPostDeath()).thenReturn(true);
        return mock;
    }

}