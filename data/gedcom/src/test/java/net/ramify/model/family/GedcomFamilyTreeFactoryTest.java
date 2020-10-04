package net.ramify.model.family;

import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class GedcomFamilyTreeFactoryTest {

    private GedcomFamilyTreeFactory testFactory = new GedcomFamilyTreeFactory();

    @Test
    void testPeopleSample() throws Exception {

        final var id = new FamilyTreeId("id");

        final var file = new File(this.getClass().getResource("/family.ged").toURI());

        final var tree = testFactory.create(id, file);

        assertEquals(3, tree.numPeople());

        {
            final var person = tree.find(new PersonId("I1")).orElse(null);
            assertNotNull(person);
            assertEquals("John Smith", person.name().toString());
            assertEquals(Gender.MALE, person.gender());
        }

    }

}