package net.ramify.model.family;

import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.relationship.type.Married;
import net.ramify.model.relationship.type.ParentChild;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GedcomFamilyTreeFactoryTest {

    private GedcomFamilyTreeFactory testFactory = new GedcomFamilyTreeFactory();

    @Test
    void testPeopleSample() throws Exception {

        final var id = new FamilyTreeId("id");

        final var file = new File(this.getClass().getResource("/family.ged").toURI());

        final var tree = testFactory.create(id, file);

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

}