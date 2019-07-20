package net.ramify.server.resource.records;

import com.google.common.collect.Iterables;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.IndividualRecord;
import net.ramify.model.record.collection.IndividualRecords;
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

    IndividualRecords search(final Records source, final RecordProto.RecordSearch search) {
        return source.individualRecords().filter(this.searchTest(search));
    }

    private Predicate<IndividualRecord> searchTest(final RecordProto.RecordSearch search) {
        final var place = search.getPlaceId().isEmpty() ? null : places.require(new PlaceId(search.getPlaceId()));
        //TODO also age/DOB search
        return record -> this.testPlace(record, place)
                && this.testName(record, search.getFirstName(), search.getLastName());
    }

    private boolean testPlace(final IndividualRecord record, final Place searchPlace) {
        if (searchPlace == null) return true;
        if (record instanceof HasPlaceId) {
            final var recordPlace = ((HasPlaceId) record).resolvePlace(places);
            return searchPlace.isParentOf(recordPlace);
        }
        final var places = record.places();
        return Iterables.any(places, searchPlace::isParentOf);
    }

    private boolean testName(final IndividualRecord record, final String firstName, final String lastName) {
        if (firstName.isBlank() && lastName.isBlank()) return true;
        final var name = record.person().name();
        return (firstName.isBlank() || name.contains(firstName))
                && (lastName.isBlank() || name.contains(lastName));
    }

}
