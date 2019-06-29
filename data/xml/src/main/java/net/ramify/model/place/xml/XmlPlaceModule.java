package net.ramify.model.place.xml;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.ramify.model.place.church.ChurchProvider;
import net.ramify.model.place.xml.church.XmlChurches;
import net.ramify.model.place.xml.place.XmlPlaces;

import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class XmlPlaceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(ChurchProvider.class).to(XmlChurchProvider.class);
    }

    @Provides
    @Singleton
    @Named("places")
    JAXBContext providePlaceContext() throws JAXBException {
        return JAXBContext.newInstance(XmlPlaces.class, XmlChurches.class);
    }

    @Provides
    @Singleton
    XmlChurchProvider provideXmlChurchProvider(@Named("places") final JAXBContext context) {
        throw new UnsupportedOperationException();
    }

}
