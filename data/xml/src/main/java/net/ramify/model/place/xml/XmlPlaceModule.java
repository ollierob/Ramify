package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.church.ChurchInfoProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.church.XmlChurchInfos;
import net.ramify.model.place.xml.place.XmlPlaces;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.net.URISyntaxException;

@XmlTransient
public class XmlPlaceModule extends PrivateModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    JAXBContext providePlaceContext() throws JAXBException {
        return JAXBContext.newInstance(XmlPlaces.class, XmlChurchInfos.class);
    }

    @Provides
    @Singleton
    ChurchInfoProvider provideChurchProvider(final JAXBContext context, @Named("data") final File data, final PlaceProvider placeProvider, final DateParser dateParser) throws JAXBException {
        return XmlChurchInfoProvider.readChurchInfo(context, data, placeProvider, dateParser);
    }

    @Provides
    @Singleton
    PlaceProvider providePlaceProvider(final JAXBContext context, @Named("data") final File data) throws URISyntaxException, JAXBException {
        return XmlPlaceProvider.readPlacesInDirectory(context, data);
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

}