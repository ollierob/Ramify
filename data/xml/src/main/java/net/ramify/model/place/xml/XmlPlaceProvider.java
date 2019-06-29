package net.ramify.model.place.xml;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Map;

public class XmlPlaceProvider implements PlaceProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlPlaceProvider.class);
    private static final File[] EMPTY_FILES = new File[0];

    private final Map<PlaceId, Place> places;

    private XmlPlaceProvider(final Map<PlaceId, Place> places) {
        this.places = places;
    }

    @CheckForNull
    @Override
    public Place get(final PlaceId id) {
        return places.get(id);
    }

    void add(final Place place) {
        places.put(place.placeId(), place);
    }

    PlaceProvider immutable() {
        return new XmlPlaceProvider(ImmutableMap.copyOf(places));
    }

    static PlaceProvider readPlacesInDirectory(final JAXBContext context, final File directory) throws JAXBException {
        final var provider = new XmlPlaceProvider(Maps.newConcurrentMap());
        final var unmarshaller = context.createUnmarshaller();
        readPlacesInDirectory(unmarshaller, directory, provider);
        return provider.immutable();
    }

    static void readPlacesInDirectory(final Unmarshaller unmarshaller, final File directory, final XmlPlaceProvider placeProvider) {
        Preconditions.checkArgument(directory.isDirectory(), "Not a directory: %s", directory);
        Preconditions.checkArgument(directory.canRead(), "Not a readable directory: %s", directory);
        //First read files
        for (final var file : MoreObjects.firstNonNull(directory.listFiles((d, name) -> name.endsWith(".xml")), EMPTY_FILES)) {
            if (file.isFile()) readPlacesInFile(unmarshaller, file, placeProvider);
        }
        //Second read subdirectories
        for (final var subdirectory : MoreObjects.firstNonNull(directory.listFiles(File::isDirectory), EMPTY_FILES)) {
            readPlacesInDirectory(unmarshaller, subdirectory, placeProvider);
        }
    }

    static void readPlacesInFile(final Unmarshaller unmarshaller, final File file, final XmlPlaceProvider placeProvider) {
        Preconditions.checkArgument(file.isFile(), "Not a file: %s", file);
        Preconditions.checkArgument(file.canRead(), "Not a readable file: %s", file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlPlaces)) return;
            final var places = (XmlPlaces) unmarshalled;
        } catch (final JAXBException jex) {
            logger.warn("Could not read places in file " + file, jex);
        }
    }

}
