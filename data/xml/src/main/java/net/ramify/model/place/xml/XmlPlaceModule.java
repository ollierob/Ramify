package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.institution.church.ChurchInfoProvider;
import net.ramify.model.place.position.PositionProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.church.XmlChurchInfos;
import net.ramify.model.place.xml.location.XmlPlaceLocations;
import net.ramify.model.place.xml.place.XmlPlaces;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;

@XmlTransient
public class XmlPlaceModule extends PrivateModule {

    @Override
    protected void configure() {
        this.expose(ChurchInfoProvider.class);
        this.expose(PlaceProvider.class);
        this.expose(PositionProvider.class);
    }

    @Provides
    @Singleton
    JAXBContext providePlaceContext() throws JAXBException {
        return JAXBContext.newInstance(XmlPlaces.class, XmlChurchInfos.class, XmlPlaceLocations.class);
    }

    @Provides
    @Singleton
    ChurchInfoProvider provideChurchProvider(final JAXBContext context, @Named("data") final File data, final PlaceProvider placeProvider, final DateParser dateParser) throws JAXBException {
        return XmlChurchInfoProvider.readChurchInfo(context, data, placeProvider, dateParser);
    }

    @Provides
    @Singleton
    PlaceProvider providePlaceProvider(final JAXBContext context, @Named("data") final File data) throws JAXBException {
        return XmlPlaceProvider.readPlacesInDirectory(context, data);
    }

    @Provides
    @Singleton
    PositionProvider providePositionProvider(final JAXBContext context, @Named("data") final File data) throws JAXBException {
        return XmlPositionProvider.readPositionsInDirectory(context, data);
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
    DateParser provideDateParser() {
        return date -> {
            if (date.length() == 4) return LocalDate.of(Integer.parseInt(date), 1, 1);
            return LocalDate.parse(date);
        };
    }

}
