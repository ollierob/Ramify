package net.ramify.model.record.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.ramify.model.date.XmlDateParser;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.XmlNameParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.model.record.archive.ArchiveProvider;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.provider.RecordsProvider;
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
        this.bind(DateParser.class).to(XmlDateParser.class);
        this.bind(NameParser.class).to(XmlNameParser.class);
        this.bind(RecordsProvider.class).to(XmlRecordProvider.class);
        this.expose(RecordsProvider.class);
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
    RecordSetProvider provideRecordSetProvider(final JAXBContext context, @Named("data") final File data, final XmlRecordProvider recordProvider, final RecordContext recordContext) throws JAXBException {
        return XmlRecordSetProvider.readRecordsInDirectory(context, data, recordProvider, recordContext);
    }

    @Provides
    @Singleton
    XmlRecordProvider provideRecordProvider(final JAXBContext context, final PlaceProvider places, final RecordContext recordContext) {
        return new XmlRecordProvider(Maps.newHashMap(), context, places, recordContext);
    }

    @Provides
    @Singleton
    ArchiveProvider archiveProvider() {
        return id -> null; //FIXME
    }

}
