package net.ramify.model.event.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.event.historic.HistoricEvent;
import net.ramify.model.event.historic.HistoricEventProvider;
import net.ramify.model.event.xml.historic.XmlHistoricEvents;
import net.ramify.model.place.Place;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.utils.file.FileTraverseUtils;
import net.ramify.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@XmlTransient
class XmlHistoricEventProvider extends AbstractMappedProvider<EventId, HistoricEvent> implements HistoricEventProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlHistoricEventProvider.class);

    XmlHistoricEventProvider(final Map<EventId, HistoricEvent> map) {
        super(map);
    }

    void addAll(final Collection<HistoricEvent> events) {
        events.forEach(event -> this.put(event.eventId(), event));
    }

    @Nonnull
    @Override
    public Set<HistoricEvent> within(final Place place, final DateRange date) {
        return this.values()
                .stream()
                .filter(event -> event.place().isParentOf(place))
                .filter(event -> event.date().intersects(date))
                .collect(Collectors.toSet());
    }

    HistoricEventProvider immutableCopy() {
        return new XmlHistoricEventProvider(this.immutableMap());
    }

    static HistoricEventProvider readRecordsInDirectory(
            final JAXBContext jaxbContext,
            final File root,
            final RecordContext recordContext) throws JAXBException {
        final var provider = new XmlHistoricEventProvider(Maps.newHashMap());
        final var unmarshaller = jaxbContext.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(
                root,
                file -> file.getName().endsWith(".xml") && file.getPath().contains("event"),
                file -> readRecordsInFile(unmarshaller, file, provider, recordContext));
        logger.info("Loaded {} record sets from {}.", provider.size(), root);
        return provider.immutableCopy();
    }

    private static void readRecordsInFile(final Unmarshaller unmarshaller, final File file, final XmlHistoricEventProvider eventProvider, final RecordContext context) {
        FileUtils.checkReadableFile(file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading events from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlHistoricEvents)) return;
            final var events = (XmlHistoricEvents) unmarshalled;
            eventProvider.addAll(events.build(context));
        } catch (final JAXBException jex) {
            logger.warn("Could not read events in file " + file + ": " + jex.getMessage());
        } catch (final Exception ex) {
            throw new RuntimeException("Error reading events in file " + file, ex);
        }
    }

}
