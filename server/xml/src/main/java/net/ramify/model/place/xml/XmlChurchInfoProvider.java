package net.ramify.model.place.xml;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.institution.church.ChurchInfo;
import net.ramify.model.place.institution.church.ChurchInfoProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.church.XmlChurchInfos;
import net.ramify.model.record.provider.RecordSetProvider;
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
import java.util.Set;

@XmlTransient
class XmlChurchInfoProvider implements ChurchInfoProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlChurchInfoProvider.class);

    private final SetMultimap<PlaceId, ChurchInfo> churches;

    private XmlChurchInfoProvider(final SetMultimap<PlaceId, ChurchInfo> churches) {
        this.churches = churches;
    }

    @CheckForNull
    @Override
    public ChurchInfo get(final PlaceId id) {
        final var infos = churches.get(id);
        if (infos.size() == 1) return infos.iterator().next();
        return null;
    }

    @Nonnull
    @Override
    public Set<ChurchInfo> within(final PlaceId id) {
        return churches.get(id);
    }

    public int size() {
        return churches.size();
    }

    private void add(final ChurchInfo info) {
        Place place = info.place();
        while (place != null) {
            churches.put(place.placeId(), info);
            place = place.parent();
        }
    }

    private XmlChurchInfoProvider immutable() {
        return new XmlChurchInfoProvider(ImmutableSetMultimap.copyOf(churches));
    }

    static ChurchInfoProvider readChurchInfo(
            final JAXBContext context,
            final File root,
            final PlaceProvider places,
            final RecordSetProvider records,
            final DateParser dateParser) throws JAXBException {
        return readChurchInfo(context.createUnmarshaller(), root, places, records, dateParser);
    }

    static ChurchInfoProvider readChurchInfo(
            final Unmarshaller unmarshaller,
            final File root,
            final PlaceProvider places,
            final RecordSetProvider records,
            final DateParser dateParser) {
        final var provider = new XmlChurchInfoProvider(HashMultimap.create());
        FileTraverseUtils.traverseSubdirectories(root, file -> file.getName().contains("church") && file.getName().endsWith(".xml"), file -> readInfo(unmarshaller, file, provider, places, records, dateParser));
        return provider.immutable();
    }

    private static void readInfo(
            final Unmarshaller unmarshaller,
            final File file,
            final XmlChurchInfoProvider infoProvider,
            final PlaceProvider places,
            final RecordSetProvider records,
            final DateParser dateParser) {
        try {
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlChurchInfos)) return;
            final var info = (XmlChurchInfos) unmarshalled;
            final var startSize = infoProvider.size();
            info.resolve(places, records, dateParser).forEach(infoProvider::add);
            final var endSize = infoProvider.size();
            logger.info("Read {} church infos from {}.", endSize - startSize, file);
        } catch (final JAXBException jex) {
            logger.warn("Could not read church data in " + file, jex);
        }
    }

}
