package net.ramify.model.place.xml;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.ramify.model.place.church.ChurchInfoProvider;
import net.ramify.model.place.xml.church.XmlChurchInfos;
import net.ramify.model.place.xml.place.XmlPlaces;

import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class XmlPlaceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(ChurchInfoProvider.class).to(XmlChurchInfoProvider.class);
    }

    @Provides
    @Singleton
    @Named("places")
    JAXBContext providePlaceContext() throws JAXBException {
        return JAXBContext.newInstance(XmlPlaces.class, XmlChurchInfos.class);
    }

    @Provides
    @Singleton
    XmlChurchInfoProvider provideXmlChurchProvider(@Named("places") final JAXBContext context) {
        throw new UnsupportedOperationException();
    }

}
