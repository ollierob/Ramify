package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceDescriptionProvider;
import net.ramify.model.place.xml.description.XmlPlaceDescriptions;
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
public class XmlPlaceDescriptionProvider implements PlaceDescriptionProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlPlaceDescriptionProvider.class);

    private final Map<PlaceId, String> descriptions;

    private XmlPlaceDescriptionProvider(final Map<PlaceId, String> descriptions) {
        this.descriptions = descriptions;
    }

    @CheckForNull
    @Override
    public String get(final PlaceId id) {
        return descriptions.get(id);
    }

    int size() {
        return descriptions.size();
    }

    void addAll(final Map<PlaceId, String> descriptons) {
        this.descriptions.putAll(descriptons);
    }

    PlaceDescriptionProvider immutable() {
        return new XmlPlaceDescriptionProvider(ImmutableMap.copyOf(descriptions));
    }

    static PlaceDescriptionProvider readDescriptionsInDirectory(final JAXBContext context, final File root) throws JAXBException {
        final var provider = new XmlPlaceDescriptionProvider(Maps.newConcurrentMap());
        final var unmarshaller = context.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(root, file -> file.getName().contains("descriptions") && file.getName().endsWith(".xml"), file -> readDescriptionsInFile(unmarshaller, file, provider));
        logger.info("Loaded {} descriptions from {}.", provider.size(), root);
        return provider.immutable();
    }

    private static void readDescriptionsInFile(final Unmarshaller unmarshaller, final File file, final XmlPlaceDescriptionProvider provider) {
        Preconditions.checkArgument(file.isFile(), "Not a file: %s", file);
        Preconditions.checkArgument(file.canRead(), "Not a readable file: %s", file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading descriptions from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlPlaceDescriptions)) return;
            final var places = (XmlPlaceDescriptions) unmarshalled;
            provider.addAll(places.descriptions());
        } catch (final JAXBException jex) {
            logger.warn("Could not read descriptions in file " + file + ": " + jex.getMessage());
        }
    }

}
