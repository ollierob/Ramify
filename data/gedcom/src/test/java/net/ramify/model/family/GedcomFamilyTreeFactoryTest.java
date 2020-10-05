package net.ramify.model.family;

import net.ramify.model.date.ExactDate;
import net.ramify.model.date.InYears;
import net.ramify.model.date.parse.DateRangeParser;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.place.Place;
import net.ramify.model.place.provider.PlaceParser;
import net.ramify.model.relationship.type.Married;
import net.ramify.model.relationship.type.ParentChild;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GedcomFamilyTreeFactoryTest {

    @Mock
    private DateRangeParser mockDateParser;
    @Mock
    private PlaceParser mockPlaceParser;

    private GedcomFamilyTreeFactory testFactory;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        when(mockPlaceParser.get(anyString())).thenAnswer(i -> mock(Place.class));
        testFactory = new GedcomFamilyTreeFactory(mockDateParser, mockPlaceParser);
    }

    @Test
    void testPeopleSample() throws Exception {

        final var id = new FamilyTreeId("id");
        final var file = new File(this.getClass().getResource("/family.ged").toURI());
        final var tree = testFactory.create(id, file);

        assertNotNull(tree);
        assertEquals(3, tree.numPeople());

        final var john = tree.find(new PersonId("I1")).orElse(null);
        assertNotNull(john);
        assertEquals("John Smith", john.name().toString());
        assertEquals(Gender.MALE, john.gender());

        final var elizabeth = tree.find(new PersonId("I2")).orElse(null);
        assertNotNull(elizabeth);

        final var james = tree.find(new PersonId("I3")).orElse(null);
        assertNotNull(james);

        assertEquals(1, tree.families().size());
        final var family = tree.families().iterator().next();

        final var marriage = family.relationshipBetween(john, elizabeth).orElse(null);
        assertThat(marriage, instanceOf(Married.class));

        final var fatherSon = family.relationshipBetween(john, james).orElse(null);
        assertThat(fatherSon, instanceOf(ParentChild.class));

    }

    @Test
    void testEventsSample() throws Exception {

        when(mockDateParser.require("15 Apr 1799")).thenReturn(ExactDate.on(1799, Month.APRIL, 15));
        when(mockDateParser.require("1821")).thenReturn(new InYears(1821));
        when(mockDateParser.require("1838")).thenReturn(new InYears(1838));
        when(mockDateParser.require("6 Jun 1841")).thenReturn(ExactDate.on(1841, Month.JUNE, 6));
        when(mockDateParser.require("22 Apr 1878")).thenReturn(ExactDate.on(1878, Month.APRIL, 22));
        when(mockDateParser.require("26 Apr 1878")).thenReturn(ExactDate.on(1878, Month.APRIL, 26));

        final var id = new FamilyTreeId("id");
        final var file = new File(this.getClass().getResource("/events.ged").toURI());
        final var tree = testFactory.create(id, file);

        assertNotNull(tree);
        assertEquals(1, tree.numPeople());

        final var person = tree.people().iterator().next();
        assertNotNull(person);

        final var events = person.events();
        assertNotNull(events);
        assertFalse(events.isEmpty());

        final var birth = events.findBirth().orElse(null);
        assertNotNull(birth);

        final var death = events.findDeath().orElse(null);
        assertNotNull(death);
        assertEquals(ExactDate.on(1878, Month.APRIL, 22), death.date());

        assertEquals(11, events.size());

    }

}