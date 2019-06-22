package net.ramify.model.record.civil.uk;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.civil.AbstractCivilRecord;
import net.ramify.model.relationship.type.ChildParent;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GeneralRegisterBirth extends AbstractCivilRecord implements GeneralRegisterRecord {

    private final GeneralRegisterRecordEntry father;
    private final GeneralRegisterRecordEntry mother;
    private final GeneralRegisterRecordBirthEntry child;
    private final PlaceId birthPlace;

    public GeneralRegisterBirth(
            final RecordId id,
            final ExactDate birthDate,
            final GeneralRegisterRecordEntry father,
            final GeneralRegisterRecordEntry mother,
            final GeneralRegisterRecordBirthEntry child,
            final PlaceId birthPlace) {
        super(id, birthDate);
        this.father = father;
        this.mother = mother;
        this.child = Objects.requireNonNull(child);
        this.birthPlace = birthPlace;
    }

    DateRange birthDate() {
        return this.date();
    }

    PlaceId birthPlace() {
        return birthPlace;
    }

    @Nonnull
    @Override
    public Family family() {
        final var builder = new FamilyBuilder();
        final var child = this.child.build(this);
        builder.addPerson(child);
        if (this.father != null) {
            final var father = this.father.build(this);
            builder.addRelationship(child, father, ChildParent::new);
        }
        if (this.mother != null) {
            final var mother = this.mother.build(this);
            builder.addRelationship(child, mother, ChildParent::new);
        }
        return builder.build();
    }

}
