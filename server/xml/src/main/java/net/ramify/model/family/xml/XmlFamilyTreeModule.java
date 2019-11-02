package net.ramify.model.family.xml;

import com.google.common.base.Preconditions;
import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import net.ramify.model.date.XmlDateParser;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.family.tree.FamilyTreeProvider;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.model.record.xml.RecordContext;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;

public class XmlFamilyTreeModule extends PrivateModule {

    @Override
    protected void configure() {
        this.bind(DateParser.class).to(XmlDateParser.class);
        //this.bind(NameParser.class).to(XmlNameParser.class);
    }

    @Provides
    @Singleton
    JAXBContext provideContext() throws JAXBException {
        return JAXBContext.newInstance(XmlFamilyTree.class);
    }

    @Provides
    @Singleton
    @Named("data")
    File provideXmlDirectory() throws URISyntaxException {
        final var data = XmlPlaceModule.class.getResource("/xml/data/families");
        Preconditions.checkState(data != null, "Could not load XML data");
        return new File(data.toURI());
    }

    @Provides
    @Exposed
    @Singleton
    FamilyTreeProvider provideTreeProvider(
            final JAXBContext context,
            @Named("data") final File data,
            final RecordContext parsers) throws JAXBException {
        return XmlFamilyTreeProvider.readTreesInDirectory(context, data, parsers);
    }

}
