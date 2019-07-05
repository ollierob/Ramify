package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlaces;
import net.ramify.utils.file.FileTraverseUtils;
import net.ramify.utils.objects.Consumers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@XmlTransient
class XmlPlaceProvider implements PlaceProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlPlaceProvider.class);

    private final Map<PlaceId, Place> places;
    private final SetMultimap<PlaceId, PlaceId> children;

    private XmlPlaceProvider(final Map<PlaceId, Place> places, final SetMultimap<PlaceId, PlaceId> children) {
        this.places = places;
        this.children = children;
    }

    @CheckForNull
    @Override
    public Place get(final PlaceId id) {
        return places.get(id);
    }

    @Nonnull
    @Override
    public Set<Place> children(final PlaceId id, final int depth, final Predicate<Place> placePredicate) {
        switch (depth) {
            case 0:
                return Collections.emptySet();
            case 1:
                return children.get(id).stream().map(this::get).filter(placePredicate).collect(Collectors.toSet());
            default:
                Preconditions.checkArgument(depth <= MAX_DEPTH, "Exceeded max depth");
                final var places = Sets.<Place>newHashSet();
                children.get(id).forEach(child -> {
                    final var place = this.get(child);
                    if (placePredicate.test(place)) places.add(this.get(child));
                    places.addAll(this.children(child.placeId(), depth - 1, placePredicate));
                });
                return places;
        }
    }

    void add(final Place place) {
        places.put(place.placeId(), place);
        Consumers.ifNonNull(place.parent(), parent -> children.put(parent.placeId(), place.placeId()));
    }

    void addAll(final Collection<Place> places) {
        places.forEach(this::add);
    }

    int size() {
        return places.size();
    }

    PlaceProvider immutable() {
        return new XmlPlaceProvider(ImmutableMap.copyOf(places), ImmutableSetMultimap.copyOf(children));
    }

    static PlaceProvider readPlacesInDirectory(final JAXBContext context, final File root) throws JAXBException {
        final var provider = new XmlPlaceProvider(Maps.newConcurrentMap(), HashMultimap.create());
        final var unmarshaller = context.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(root, file -> file.getName().endsWith(".xml"), file -> readPlacesInFile(unmarshaller, file, provider));
        logger.info("Loaded {} places from {}.", provider.size(), root);
        return provider.immutable();
    }

    private static void readPlacesInFile(final Unmarshaller unmarshaller, final File file, final XmlPlaceProvider placeProvider) {
        Preconditions.checkArgument(file.isFile(), "Not a file: %s", file);
        Preconditions.checkArgument(file.canRead(), "Not a readable file: %s", file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading places from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlPlaces)) return;
            final var places = (XmlPlaces) unmarshalled;
            placeProvider.addAll(places.places(placeProvider));
        } catch (final JAXBException jex) {
            logger.warn("Could not read places in file " + file + ": " + jex.getMessage());
        }
    }

}
