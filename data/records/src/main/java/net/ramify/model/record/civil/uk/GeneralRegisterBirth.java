package net.ramify.model.record.civil.uk;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.place.Place;
import net.ramify.model.record.GenericRecordEntry;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.civil.AbstractCivilRecord;
import net.ramify.model.record.type.BirthRecord;
import net.ramify.model.relationship.type.ChildParent;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GeneralRegisterBirth extends AbstractCivilRecord implements GeneralRegisterRecord, BirthRecord {

    private final GenericRecordEntry father;
    private final GenericRecordEntry mother;
    private final GeneralRegisterRecordBirthEntry child;
    private final Place birthPlace;

    public GeneralRegisterBirth(
            final RecordId id,
            final ExactDate birthDate,
            final GeneralRegisterRecordBirthEntry child,
            final GenericRecordEntry father,
            final GenericRecordEntry mother,
            final Place birthPlace) {
        super(id, birthDate);
        this.father = father;
        this.mother = mother;
        this.child = Objects.requireNonNull(child);
        this.birthPlace = birthPlace;
    }

    DateRange birthDate() {
        return this.date();
    }

    Place birthPlace() {
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

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.BIRTH;
    }

    @Nonnull
    @Override
    public Place place() {
        return this.birthPlace();
    }

}
