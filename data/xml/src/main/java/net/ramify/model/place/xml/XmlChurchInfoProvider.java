package net.ramify.model.place.xml;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.church.ChurchInfo;
import net.ramify.model.place.church.ChurchInfoProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.church.XmlChurchInfos;
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
class XmlChurchInfoProvider implements ChurchInfoProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlChurchInfoProvider.class);

    private final Map<PlaceId, ChurchInfo> churches;

    private XmlChurchInfoProvider(final Map<PlaceId, ChurchInfo> churches) {
        this.churches = churches;
    }

    @CheckForNull
    @Override
    public ChurchInfo get(final PlaceId id) {
        return churches.get(id);
    }

    private void add(final ChurchInfo info) {
        churches.put(info.placeId(), info);
    }

    private XmlChurchInfoProvider immutable() {
        return new XmlChurchInfoProvider(ImmutableMap.copyOf(churches));
    }

    static ChurchInfoProvider readChurchInfo(final JAXBContext context, final File root, final PlaceProvider placeProvider, final DateParser dateParser) throws JAXBException {
        return readChurchInfo(context.createUnmarshaller(), root, placeProvider, dateParser);
    }

    static ChurchInfoProvider readChurchInfo(final Unmarshaller unmarshaller, final File root, final PlaceProvider placeProvider, final DateParser dateParser) throws JAXBException {
        final var provider = new XmlChurchInfoProvider(Maps.newConcurrentMap());
        FileTraverseUtils.traverseSubdirectories(root, file -> file.getName().contains("church") && file.getName().endsWith(".xml"), file -> readInfo(unmarshaller, file, provider, placeProvider, dateParser));
        return provider.immutable();
    }

    private static void readInfo(final Unmarshaller unmarshaller, final File file, final XmlChurchInfoProvider infoProvider, final PlaceProvider placeProvider, final DateParser dateParser) {
        try {
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlChurchInfos)) return;
            logger.info("Reading church data in {}", file);
            final var info = (XmlChurchInfos) unmarshalled;
            info.resolve(placeProvider, dateParser).forEach(infoProvider::add);
        } catch (final JAXBException jex) {
            logger.warn("Could not read church data in file " + file, jex);
        }
    }

}
