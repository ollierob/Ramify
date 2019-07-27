package net.ramify.model.record.residence.uk;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.model.relationship.RelationshipFactory;

import javax.annotation.Nonnull;
import java.time.Month;

public class Census1821Record extends CensusRecord implements HasPlace {

    public static final ExactDate CENSUS_DATE = ExactDate.on(1821, Month.MAY, 28);

    private final Person head;
    private final Table<Gender, Age, Integer> ageCounts;

    public Census1821Record(
            final RecordId id,
            final RecordSetId recordSetId,
            final Place place,
            final Person head,
            final Table<Gender, Age, Integer> ageCounts) {
        super(id, recordSetId, CENSUS_DATE, place);
        this.head = head;
        this.ageCounts = ageCounts;
    }

    @Nonnull
    @Override
    public Family family() {
        final var builder = new FamilyBuilder();
        builder.addPerson(head);
        this.enumerate(builder);
        return builder.build();
    }

    private void enumerate(final FamilyBuilder builder) {
        for (final var cell : ageCounts.cellSet()) {
            for (int i = 0; i < cell.getValue(); i++) {
                builder.addRelationship(head, this.anonymousPerson(cell.getRowKey(), cell.getColumnKey()), RelationshipFactory.UNKNOWN);
            }
        }
    }

    private Person anonymousPerson(final Gender gender, final Age age) {
        final var id = PersonId.random();
        final var birth = new GenericBirth(id, age.birthDate(CENSUS_DATE));
        final var residence = new GenericResidence(id, CENSUS_DATE, this.place());
        return new GenericRecordPerson(id, Name.UNKNOWN, gender, ImmutableSet.of(birth, residence), "Anonymous");
    }

}
