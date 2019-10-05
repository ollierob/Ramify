package net.ramify.server.data.family;

import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.FamilyTreeMeta;
import net.ramify.model.family.tree.FamilyTreeProvider;
import net.ramify.model.family.tree.SingletonFamilyTree;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.relationship.type.Married;
import net.ramify.model.relationship.type.ParentChild;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Set;

@Singleton
public class StubFamilyTreeProvider implements FamilyTreeProvider {

    private static final FamilyTree DEFAULT_TREE;

    static {
        final var builder = new FamilyBuilder();
        final var grandfather = male("Grandaddy");
        builder.addPerson(grandfather);
        final var father = male("Daddy");
        builder.addPerson(father);
        builder.addRelationship(grandfather, father, ParentChild::new);
        final var mother = mother();
        builder.addPerson(mother);
        builder.addRelationship(father, mother, Married::new);
        final var self = self();
        builder.addPerson(self);
        builder.addRelationship(father, self, ParentChild::new);
        builder.addRelationship(mother, self, ParentChild::new);
        final var family = builder.build();
        DEFAULT_TREE = new SingletonFamilyTree(meta(family), family);
    }

    private static Person male(final String name) {
        return new GenericRecordPerson(
                new PersonId(name),
                new ForenameSurname(name, "Robertshaw"),
                Gender.MALE,
                Collections.emptySet(),
                null);
    }

    private static Person mother() {
        return new GenericRecordPerson(
                new PersonId("mother"),
                new ForenameSurname("Mummy", "Robertshaw"),
                Gender.FEMALE,
                Collections.emptySet(),
                null);
    }

    private static Person self() {
        return new GenericRecordPerson(
                new PersonId("self"),
                new ForenameSurname("Self", "Robertshaw"),
                Gender.MALE,
                Collections.emptySet(),
                null);
    }

    private static FamilyTreeMeta meta(final Family family) {
        return new FamilyTreeMeta() {

            @Nonnull
            @Override
            public String name() {
                return "Robertshaw";
            }

            @Override
            public int numPeople() {
                return family.people().size();
            }

            @Nonnull
            @Override
            public FamilyTreeId familyTreeId() {
                return new FamilyTreeId("stub");
            }
        };
    }

    @Nonnull
    @Override
    public Set<FamilyTreeId> allIds() {
        return Collections.singleton(DEFAULT_TREE.familyTreeId());
    }

    @CheckForNull
    @Override
    public FamilyTree get(final FamilyTreeId id) {
        return DEFAULT_TREE;
    }

}
