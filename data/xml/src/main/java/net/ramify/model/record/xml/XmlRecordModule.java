package net.ramify.model.record.xml;

import com.google.common.base.Preconditions;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.xml.collection.XmlRecordSets;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.net.URISyntaxException;

@XmlTransient
public class XmlRecordModule extends PrivateModule {

    @Override
    protected void configure() {
        this.expose(RecordSetProvider.class);
    }

    @Provides
    @Singleton
    JAXBContext provideContext() throws JAXBException {
        return JAXBContext.newInstance(XmlRecordSets.class);
    }

    @Provides
    @Nonnull
    @Singleton
    @Named("data")
    File provideXmlDirectory() throws URISyntaxException {
        final var places = XmlPlaceModule.class.getResource("/xml/data");
        Preconditions.checkState(places != null, "Could not load XML data");
        return new File(places.toURI());
    }

    @Provides
    @Singleton
    RecordSetProvider provideRecordSetProvider(final JAXBContext context, @Named("data") final File data) throws JAXBException {
        return XmlRecordSetProvider.readRecordsInDirectory(context, data);
    }

}
