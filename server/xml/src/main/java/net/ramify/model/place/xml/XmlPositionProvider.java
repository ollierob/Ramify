package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.position.Position;
import net.ramify.model.place.position.PositionProvider;
import net.ramify.model.place.xml.location.XmlPositions;
import net.ramify.utils.file.FileTraverseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.Map;

@XmlTransient
public class XmlPositionProvider implements PositionProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlPositionProvider.class);

    private final Map<PlaceId, Position> positions;

    private XmlPositionProvider(final Map<PlaceId, Position> positions) {
        this.positions = positions;
    }

    int size() {
        return positions.size();
    }

    @CheckForNull
    @Override
    public Position get(final PlaceId key) {
        return positions.get(key);
    }

    void putAll(final Map<? extends PlaceId, ? extends Position> positions) {
        this.positions.putAll(positions);
    }

    PositionProvider immutable() {
        return new XmlPositionProvider(ImmutableMap.copyOf(positions));
    }

    static PositionProvider readPositionsInDirectory(final JAXBContext context, final File root) throws JAXBException {
        final var provider = new XmlPositionProvider(Maps.newConcurrentMap());
        final var unmarshaller = context.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(root, XmlPositionProvider::isPositionFile, file -> readPositionsInFile(unmarshaller, file, provider));
        logger.info("Loaded {} places from {}.", provider.size(), root);
        return provider.immutable();
    }

    static boolean isPositionFile(final File file) {
        return file.getName().endsWith(".xml")
                && !file.getPath().contains("_records");
    }

    private static void readPositionsInFile(final Unmarshaller unmarshaller, final File file, final XmlPositionProvider locationProvider) {
        Preconditions.checkArgument(file.isFile(), "Not a file: %s", file);
        Preconditions.checkArgument(file.canRead(), "Not a readable file: %s", file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading locations from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlPositions)) return;
            final var places = (XmlPositions) unmarshalled;
            locationProvider.putAll(places.positions());
        } catch (final JAXBException jex) {
            logger.warn("Ignoring locations in file " + file);
        } catch (final RuntimeException rex) {
            throw new RuntimeException("Could not read locations in file " + file, rex);
        }
    }

}
