package net.ramify.server.resource.records;

import com.google.common.collect.Iterables;
import net.ramify.model.person.Person;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.Record;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.proto.RecordProto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Predicate;

@Singleton
class RecordSearch {

    private final PlaceProvider places;

    @Inject
    RecordSearch(final PlaceProvider places) {
        this.places = places;
    }

    Records search(final Records source, final RecordProto.RecordSearch search) {
        return source.filter(this.searchTest(search));
    }

    private Predicate<Record> searchTest(final RecordProto.RecordSearch search) {
        final var place = search.getPlaceId().isEmpty() ? null : places.require(new PlaceId(search.getPlaceId()));
        //TODO also age/DOB search
        return record -> this.testPlace(record, place)
                && this.testName(record, search.getFirstName(), search.getLastName());
    }

    private boolean testPlace(final Record record, final Place place) {
        if (place == null) return true;
        if (record instanceof HasPlaceId) {
            final var recordPlace = ((HasPlaceId) record).resolvePlace(places);
            return place.isParentOf(recordPlace);
        }
        return false;
    }

    private boolean testName(final Record record, final String firstName, final String lastName) {
        return Iterables.any(record.people(), person -> this.testPerson(person, firstName, lastName));
    }

    private boolean testPerson(final Person person, final String firstName, final String lastName) {
        return person.name().contains(firstName) || person.name().contains(lastName);
    }

}
