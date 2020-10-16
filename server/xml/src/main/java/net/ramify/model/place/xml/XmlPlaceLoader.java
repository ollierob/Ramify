package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ramify.model.ParserContext;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.provider.PlaceHierarchyProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlaces;
import net.ramify.utils.file.FileTraverseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Arrays;

class XmlPlaceLoader {

    private static final Logger logger = LoggerFactory.getLogger(XmlPlaceLoader.class);

    static XmlPlaceLoader readPlacesInCountryRoot(
            final JAXBContext jaxbContext,
            final File countryRoot,
            final ParserContext context) throws JAXBException {
        final var places = new XmlPlaceProvider(Maps.newHashMap(), HashMultimap.create(), Sets.newHashSet());
        final var hierarchies = new XmlPlaceHierarchyProvider();
        final var unmarshaller = jaxbContext.createUnmarshaller();
        for (final File dir : countryRoots(countryRoot)) {
            final var countryIso = CountryIso.valueOf(dir.getName());
            final var placeContext = new PlaceParserContext(context.nameParser(), context.dateParser(), countryIso, places, hierarchies);
            FileTraverseUtils.traverseSubdirectories(dir, XmlPlaceLoader::includeFile, file -> readPlacesInFile(unmarshaller, file, places, hierarchies, placeContext));
            logger.info("Loaded {} places from {}.", places.size(), dir);
        }
        return new XmlPlaceLoader(places.immutable(), hierarchies.immutable());
    }

    private static File[] countryRoots(final File root) {
        final var roots = root.listFiles(File::isDirectory);
        Arrays.sort(roots);
        return roots;
    }

    private static void readPlacesInFile(
            final Unmarshaller unmarshaller,
            final File file,
            final XmlPlaceProvider placeProvider,
            final XmlPlaceHierarchyProvider hierarchyProvider,
            final PlaceParserContext context) {

        Preconditions.checkArgument(file.isFile(), "Not a file: %s", file);
        Preconditions.checkArgument(file.canRead(), "Not a readable file: %s", file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);

        try {
            logger.info("Reading places from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlPlaces)) return;
            final var places = ((XmlPlaces) unmarshalled).places();
            hierarchyProvider.addAll(context, places);
            placeProvider.addAll(context, places);
        } catch (final JAXBException jex) {
            logger.warn("Could not read places in file " + file + ": " + jex.getMessage());
        } catch (final RuntimeException rex) {
            throw new RuntimeException("Error reading places in file: " + file, rex);
        }

    }

    private static boolean includeFile(final File file) {
        if (!file.getName().endsWith(".xml")) return false;
        final var path = file.getPath();
        return !path.contains("_records")
                && !path.contains("_events");
    }

    private final PlaceProvider places;
    private final PlaceHierarchyProvider hierarchies;

    private XmlPlaceLoader(final PlaceProvider places, final PlaceHierarchyProvider hierarchies) {
        this.places = places;
        this.hierarchies = hierarchies;
    }

    PlaceProvider places() {
        return places;
    }

    PlaceHierarchyProvider hierarchies() {
        return hierarchies;
    }

}
