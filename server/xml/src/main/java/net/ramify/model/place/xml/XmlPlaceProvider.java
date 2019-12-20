package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import net.meerkat.functions.consumers.Consumers;
import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceGroupProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.xml.place.XmlPlaces;
import net.ramify.utils.file.FileTraverseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.Arrays;
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
    private final Set<Country> countries;

    private XmlPlaceProvider(final Map<PlaceId, Place> places, final SetMultimap<PlaceId, PlaceId> children, final Set<Country> countries) {
        this.places = places;
        this.children = children;
        this.countries = countries;
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

    @Nonnull
    @Override
    public Set<Country> countries() {
        return countries;
    }

    @Override
    public Set<Place> findByName(final String name, final int limit, final PlaceId within) {
        final var findString = name.toLowerCase();
        final var parent = within == null ? null : this.require(within);
        return places.values().stream()
                .filter(place -> place.name().toLowerCase().contains(findString))
                .filter(place -> parent == null || parent.isParentOf(place))
                .limit(limit)
                .collect(Collectors.toSet());
    }

    void add(final Place place) {
        places.put(place.placeId(), place);
        Consumers.ifNonNull(place.parent(), parent -> children.put(parent.placeId(), place.placeId()));
        place.ultimateParent().as(Country.class).ifPresent(countries::add);
    }

    void addAll(final Collection<Place> places) {
        places.forEach(this::add);
    }

    int size() {
        return places.size();
    }

    PlaceProvider immutable() {
        return new XmlPlaceProvider(ImmutableMap.copyOf(places), ImmutableSetMultimap.copyOf(children), ImmutableSet.copyOf(countries));
    }

    static PlaceProvider readPlacesInCountryRoot(
            final JAXBContext jaxbContext,
            final File countryRoot,
            final PlaceGroupProvider groupProvider,
            final ParserContext context) throws JAXBException {
        final var provider = new XmlPlaceProvider(Maps.newHashMap(), HashMultimap.create(), Sets.newHashSet());
        final var unmarshaller = jaxbContext.createUnmarshaller();
        for (final File dir : countryRoots(countryRoot)) {
            final var countryIso = CountryIso.valueOf(dir.getName());
            final var placeContext = new PlaceParserContext(context.nameParser(), context.dateParser(), countryIso, provider, groupProvider);
            FileTraverseUtils.traverseSubdirectories(dir, XmlPlaceProvider::includeFile, file -> readPlacesInFile(unmarshaller, file, provider, placeContext));
            logger.info("Loaded {} places from {}.", provider.size(), dir);
        }
        return provider.immutable();
    }

    private static File[] countryRoots(final File root) {
        final var roots = root.listFiles(File::isDirectory);
        Arrays.sort(roots);
        return roots;
    }

    private static boolean includeFile(final File file) {
        final var name = file.getName();
        return name.endsWith(".xml")
                && !name.contains("_records");
    }

    private static void readPlacesInFile(final Unmarshaller unmarshaller, final File file, final XmlPlaceProvider placeProvider, final PlaceParserContext context) {
        Preconditions.checkArgument(file.isFile(), "Not a file: %s", file);
        Preconditions.checkArgument(file.canRead(), "Not a readable file: %s", file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading places from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlPlaces)) return;
            final var places = (XmlPlaces) unmarshalled;
            placeProvider.addAll(places.places(context));
        } catch (final JAXBException jex) {
            logger.warn("Could not read places in file " + file + ": " + jex.getMessage());
        } catch (final RuntimeException rex) {
            throw new RuntimeException("Error reading places in file: " + file, rex);
        }
    }

}
