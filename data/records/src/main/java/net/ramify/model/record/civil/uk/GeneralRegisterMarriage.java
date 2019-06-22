package net.ramify.model.record.civil.uk;

import net.ramify.model.date.ExactDate;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.civil.AbstractCivilRecord;
import net.ramify.model.relationship.type.ChildParent;
import net.ramify.model.relationship.type.Married;
import net.ramify.model.relationship.type.Unknown;

import javax.annotation.Nonnull;

public class GeneralRegisterMarriage extends AbstractCivilRecord implements GeneralRegisterRecord {

    private final PlaceId marriagePlace;
    private final GeneralRegisterRecordEntry groom;
    private final GeneralRegisterRecordEntry bride;
    private final GeneralRegisterRecordEntry groomFather;
    private final GeneralRegisterRecordEntry brideFather;
    private final GeneralRegisterRecordEntry firstWitness;
    private final GeneralRegisterRecordEntry secondWitness;

    public GeneralRegisterMarriage(
            final RecordId id,
            final ExactDate date,
            final PlaceId marriagePlace,
            final GeneralRegisterRecordEntry groom,
            final GeneralRegisterRecordEntry bride,
            final GeneralRegisterRecordEntry groomFather,
            final GeneralRegisterRecordEntry brideFather,
            final GeneralRegisterRecordEntry firstWitness,
            final GeneralRegisterRecordEntry secondWitness) {
        super(id, date);
        this.marriagePlace = marriagePlace;
        this.groom = groom;
        this.bride = bride;
        this.groomFather = groomFather;
        this.brideFather = brideFather;
        this.firstWitness = firstWitness;
        this.secondWitness = secondWitness;
    }

    @Nonnull
    @Override
    public Family family() {
        final var builder = new FamilyBuilder();
        final var groom = this.groom.build(this);
        final var bride = this.bride.build(this);
        builder.addRelationship(bride, groom, Married::new);
        if (groomFather != null) {
            final var father = groomFather.build(this);
            builder.addRelationship(groom, father, ChildParent::new);
        }
        if (brideFather != null) {
            final var father = brideFather.build(this);
            builder.addRelationship(bride, father, ChildParent::new);
        }
        if (firstWitness != null) {
            final var witness = firstWitness.build(this);
            builder.addRelationship(groom, witness, Unknown::new);
        }
        if (secondWitness != null) {
            final var witness = secondWitness.build(this);
            builder.addRelationship(groom, witness, Unknown::new);
        }
        return builder.build();
    }

}
