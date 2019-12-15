package net.ramify.model.record.civil.uk;

import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.Place;
import net.ramify.model.record.GenericRecordEntry;
import net.ramify.model.relationship.RelationshipFactory;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class GeneralRegisterRecordDeathInformant extends GenericRecordEntry {

    private final RelationshipFactory relationshipToDeceased;

    public GeneralRegisterRecordDeathInformant(
            @Nonnull final PersonId id,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final Place residence,
            @CheckForNull final String occupation,
            final RelationshipFactory relationshipToDeceased) {
        super(id, name, gender, residence, occupation, null, false);
        this.relationshipToDeceased = relationshipToDeceased;
    }

    RelationshipFactory relationshipToDeceased() {
        return relationshipToDeceased;
    }

}
