package net.ramify.model.event.xml;

import com.google.common.base.Preconditions;
import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import net.ramify.model.event.historic.HistoricEventProvider;
import net.ramify.model.event.xml.historic.XmlHistoricEvents;
import net.ramify.model.record.xml.RecordContext;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.net.URISyntaxException;

@XmlTransient
class XmlHistoricEventModule extends PrivateModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    JAXBContext provideContext() throws JAXBException {
        return JAXBContext.newInstance(XmlHistoricEvents.class);
    }

    @Provides
    @Nonnull
    @Singleton
    @Named("data")
    File provideXmlDirectory() throws URISyntaxException {
        final var data = XmlHistoricEventModule.class.getResource("/xml/data/places");
        Preconditions.checkState(data != null, "Could not load XML data");
        return new File(data.toURI());
    }

    @Provides
    @Exposed
    @Singleton
    HistoricEventProvider historicEventProvider(final JAXBContext context, @Named("data") final File data, final RecordContext recordContext) throws JAXBException {
        return XmlHistoricEventProvider.readRecordsInDirectory(context, data, recordContext);
    }

}
