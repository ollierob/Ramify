package net.ramify.model.record.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.provider.RecordSetRelativesProvider;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.model.record.xml.collection.XmlRecordSets;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

@XmlTransient
public class XmlRecordModule extends PrivateModule {

    @Override
    protected void configure() {
        this.expose(RecordSetProvider.class);
        this.bind(RecordsProvider.class).to(XmlRecordProvider.class);
        this.expose(RecordsProvider.class);
        this.expose(RecordSetRelativesProvider.class);
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
        final var data = XmlPlaceModule.class.getResource("/xml/data");
        Preconditions.checkState(data != null, "Could not load XML data");
        return new File(data.toURI());
    }

    @Provides
    @Singleton
    XmlRecordSetProvider provideRecordSetProvider(
            final JAXBContext context,
            @Named("data") final File data,
            final XmlRecordSetRelativesProvider relatives,
            final RecordContext recordContext) throws JAXBException {
        return XmlRecordSetProvider.readRecordsInDirectory(context, data, relatives, recordContext);
    }

    @Provides
    @Singleton
    RecordSetProvider provideRecordSetProvider(final XmlRecordSetProvider recordSetProvider) {
        return recordSetProvider.immutable();
    }

    @Provides
    @Singleton
    Map<RecordSetId, File> provideRecordSetFiles(final XmlRecordSetProvider recordSetProvider) {
        return recordSetProvider.recordSetFiles();
    }

    @Provides
    @Singleton
    XmlRecordProvider provideRecordProvider(
            final JAXBContext context,
            final RecordContext recordContext,
            final Map<RecordSetId, File> recordSetFiles,
            final RecordSetProvider recordSets) {
        return new XmlRecordProvider(recordSetFiles, context, recordContext, recordSets);
    }

    @Provides
    @Singleton
    XmlRecordSetRelativesProvider relativesProvider() {
        return new XmlRecordSetRelativesProvider(Maps.newHashMap());
    }

    @Provides
    @Singleton
    RecordSetRelativesProvider relativesProvider(final XmlRecordSetRelativesProvider relatives, final RecordSetProvider recordSets) {
        return relatives.immutable(recordSets);
    }

}
