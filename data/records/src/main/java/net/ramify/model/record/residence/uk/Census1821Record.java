package net.ramify.model.record.residence.uk;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.merge.UniqueEventMerger;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.PersonWithAdditionalEvents;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.model.relationship.RelationshipFactory;

import javax.annotation.Nonnull;
import java.time.Month;
import java.time.Period;
import java.util.Optional;

public class Census1821Record extends CensusRecord implements HasPlace {

    public static final ExactDate CENSUS_DATE = ExactDate.on(1821, Month.MAY, 28);
    private static final Period MIN_HEAD_AGE = Period.ofYears(16);

    private final Person head;
    private final UniqueEventMerger eventMerger;
    private final Table<Gender, Age, Integer> ageCounts;

    public Census1821Record(
            final RecordId id,
            final RecordSet recordSet,
            final Place place,
            final Person head,
            final Table<Gender, Age, Integer> ageCounts,
            final UniqueEventMerger eventMerger) {
        super(id, recordSet, CENSUS_DATE, place);
        this.head = head;
        this.eventMerger = eventMerger;
        Preconditions.checkArgument(!ageCounts.isEmpty());
        this.ageCounts = ageCounts;
    }

    @Nonnull
    @Override
    public Family family() {

        var head = this.head;
        var ageCounts = this.ageCounts;

        final var headAge = this.onlyAdultAge(head);
        if (headAge.isPresent()) {
            final var age = headAge.get();
            ageCounts = HashBasedTable.create(ageCounts);
            ageCounts.put(head.gender(), age, ageCounts.get(head.gender(), age) - 1);
            head = this.agedPerson(head, age);
        }

        final var builder = new FamilyBuilder(head);

        for (final var cell : ageCounts.cellSet()) {
            for (int i = 0; i < cell.getValue(); i++) {
                final var gender = cell.getRowKey();
                final var age = cell.getColumnKey();
                builder.addRelationship(head, this.anonymousPerson(gender, age), RelationshipFactory.UNKNOWN);
            }
        }

        return builder.build();

    }

    private Person anonymousPerson(final Gender gender, final Age age) {
        final var personId = PersonId.random();
        final var birth = this.birthEvent(personId, age);
        final var residence = this.eventBuilder().withGivenAge(age).toResidence(personId);
        final var events = new MutablePersonEventSet(eventMerger, birth, residence);
        return new GenericRecordPerson(personId, Name.UNKNOWN, gender, events);
    }

    private Person agedPerson(final Person person, final Age age) {
        final var birth = this.birthEvent(person.personId(), age);
        return new PersonWithAdditionalEvents(person, birth);
    }

    private Optional<Age> onlyAdultAge(final Person person) {
        final var ages = ageCounts.row(person.gender());
        switch (ages.size()) {
            case 0:
                throw new IllegalStateException();
            case 1: {
                final var only = ages.entrySet().iterator().next();
                return only.getValue() == 1 ? Optional.of(only.getKey()) : Optional.empty();
            }
            default:
                final var adultAges = Maps.filterKeys(ages, age -> age.isSameOrOlderThan(MIN_HEAD_AGE));
                if (adultAges.size() != 1) return Optional.empty();
                final var only = adultAges.entrySet().iterator().next();
                return only.getValue() == 1 ? Optional.of(only.getKey()) : Optional.empty();
        }
    }

}
