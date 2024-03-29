package net.ramify.server.resource.records;

import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.IndividualRecord;
import net.ramify.model.record.collection.IndividualRecords;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.strategy.match.person.name.NameMatcher;

import javax.annotation.CheckForNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Predicate;

@Singleton
class RecordSearch {

    private final PlaceProvider places;
    private final NameMatcher nameMatch;
    private final NameParser nameParser;

    @Inject
    RecordSearch(final PlaceProvider places, final NameMatcher nameMatch, final NameParser nameParser) {
        this.places = places;
        this.nameMatch = nameMatch;
        this.nameParser = nameParser;
    }

    IndividualRecords searchIndividuals(final Records source, final RecordProto.RecordSearch search) {
        return source.individualRecords(true).filter(this.searchIndividualTest(search));
    }

    private Predicate<IndividualRecord> searchIndividualTest(final RecordProto.RecordSearch search) {
        final var place = this.searchPlace(search);
        final var name = this.searchName(search);
        //TODO also age/DOB search
        return record -> this.testPlace(record, place) && this.testName(record, name);
    }

    private Name searchName(final RecordProto.RecordSearch search) {
        final var first = search.getFirstName();
        final var last = search.getLastName();
        if (first.isBlank() && last.isBlank()) return null;
        if (first.isBlank()) return new ForenameSurname(null, last);
        if (last.isBlank()) return new ForenameSurname(first, null);
        return nameParser.parse(first + ' ' + last);
    }

    @CheckForNull
    private Place searchPlace(final RecordProto.RecordSearch search) {
        return search.getPlaceId().isEmpty() ? null : places.require(new PlaceId(search.getPlaceId()));
    }

    private boolean testPlace(final IndividualRecord record, final Place searchPlace) {
        if (searchPlace == null) return true;
        if (record instanceof HasPlaceId) {
            final var recordPlace = ((HasPlaceId) record).resolvePlace(places);
            //return searchPlace.isParentOf(recordPlace);
            return false; //FIXME use hierarchy
        }
        final var places = record.places();
        //return Iterables.any(places, searchPlace::isParentOf);
        return false; //FIXME use hierarchy
    }

    private boolean testName(final IndividualRecord record, final Name searchName) {
        return searchName == null || nameMatch.match(record.person().name(), searchName).isMatch();
    }

}
