package net.ramify.model.place.xml;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.group.DefaultPlaceGroup;
import net.ramify.model.place.provider.PlaceGroupProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.group.XmlPlaceGroups;
import net.ramify.utils.file.FileTraverseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

class XmlPlaceGroupProvider extends AbstractMappedProvider<PlaceGroupId, PlaceGroup> implements PlaceGroupProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlPlaceGroupProvider.class);

    private final Map<PlaceId, PlaceGroup> reverseMappings;

    XmlPlaceGroupProvider(final Map<PlaceGroupId, PlaceGroup> map, final Map<PlaceId, PlaceGroup> reverseMappings) {
        super(map);
        this.reverseMappings = reverseMappings;
    }

    void addAll(final Collection<PlaceGroup> groups) {
        groups.forEach(this::add);
    }

    void add(final PlaceGroup group) {
        this.put(group.id(), group);
        group.children().forEach(child -> reverseMappings.put(child.placeId(), group));
    }

    @Override
    public PlaceGroup getGroup(final PlaceId placeId) {
        return reverseMappings.get(placeId);
    }

    @Override
    public PlaceGroup synthesize(Place defaultPlace, Set<Place> otherChildren) {
        return new DefaultPlaceGroup(defaultPlace.placeGroupId(), defaultPlace.name(), defaultPlace, otherChildren);
    }

    PlaceGroupProvider immutable() {
        return new XmlPlaceGroupProvider(this.immutableMap(), ImmutableMap.copyOf(reverseMappings));
    }

    static PlaceGroupProvider readGroupsInDirectory(final JAXBContext context, final File root, final PlaceProvider placeProvider) throws JAXBException {
        final var groupProvider = new XmlPlaceGroupProvider(Maps.newHashMap(), Maps.newHashMap());
        final var unmarshaller = context.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(root, file -> file.getName().endsWith(".xml"), file -> readGroupsInFile(unmarshaller, file, groupProvider, placeProvider));
        logger.info("Loaded {} place groups from {}.", groupProvider.size(), root);
        return groupProvider.immutable();
    }

    private static void readGroupsInFile(final Unmarshaller unmarshaller, final File file, final XmlPlaceGroupProvider groupProvider, final PlaceProvider placeProvider) {
        try {
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlPlaceGroups)) return;
            final var groups = (XmlPlaceGroups) unmarshalled;
            groupProvider.addAll(groups.placeGroups(placeProvider));
        } catch (final JAXBException jex) {
            logger.warn("Could not read groups in file " + file + ": " + jex.getMessage());
        } catch (final RuntimeException rex) {
            throw new RuntimeException("Could not read groups in file " + file, rex);
        }
    }

}
