package net.ramify.model.record.civil.uk;

import net.ramify.model.occupation.Occupation;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.PlaceId;
import net.ramify.model.relationship.RelationshipFactory;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class GeneralRegisterRecordDeathInformant extends GeneralRegisterRecordEntry {

    private final RelationshipFactory relationshipToDeceased;

    public GeneralRegisterRecordDeathInformant(
            @Nonnull final PersonId id,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final PlaceId residence,
            @CheckForNull final Occupation occupation,
            final RelationshipFactory relationshipToDeceased) {
        super(id, name, gender, residence, occupation, null, false);
        this.relationshipToDeceased = relationshipToDeceased;
    }

    RelationshipFactory relationshipToDeceased() {
        return relationshipToDeceased;
    }

}
