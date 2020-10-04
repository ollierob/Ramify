package net.ramify.model.place.xml;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.utils.collections.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@XmlTransient
class XmlPlaceProvider implements PlaceProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlPlaceProvider.class);

    private final Map<PlaceId, Place> places;
    private final SetMultimap<PlaceGroupId, PlaceId> groupIds;
    private final Set<Country> countries;

    XmlPlaceProvider(
            final Map<PlaceId, Place> places,
            final SetMultimap<PlaceGroupId, PlaceId> groupIds,
            final Set<Country> countries) {
        this.places = places;
        this.groupIds = groupIds;
        this.countries = countries;
    }

    @CheckForNull
    @Override
    public Place get(final PlaceId id) {
        return places.get(id);
    }

    @Nonnull
    @Override
    public Set<Country> countries(final boolean onlyTopLevel) {
        if (onlyTopLevel) return Sets.filter(countries, country -> country.parent() == null);
        return countries;
    }

    @Nonnull
    @Override
    public Set<Place> findByGroup(final PlaceGroupId groupId) {
        return SetUtils.eagerTransform(groupIds.get(groupId), this::require);
    }

    void addAll(final PlaceParserContext context, final Collection<XmlPlace> places) {
        for (final var place : places) {
            place.places(context).forEach(this::add);
        }
    }

    void add(final Place place) {
        places.put(place.placeId(), place);
        //Consumers.ifNonNull(place.parent(), parent -> children.put(parent.placeId(), place.placeId()));
        place.as(Country.class).ifPresent(countries::add);
        groupIds.put(place.placeGroupId(), place.placeId());
    }

    int size() {
        return places.size();
    }

    PlaceProvider immutable() {
        return new XmlPlaceProvider(
                ImmutableMap.copyOf(places),
                ImmutableSetMultimap.copyOf(groupIds),
                ImmutableSet.copyOf(countries));
    }

}
