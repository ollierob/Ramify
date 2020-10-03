package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.ramify.model.ParserContext;
import net.ramify.model.place.position.PositionProvider;
import net.ramify.model.place.provider.PlaceDescriptionProvider;
import net.ramify.model.place.provider.PlaceGroupProvider;
import net.ramify.model.place.provider.PlaceHierarchyProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.archives.XmlArchives;
import net.ramify.model.place.xml.description.XmlPlaceDescriptions;
import net.ramify.model.place.xml.location.XmlPositions;
import net.ramify.model.place.xml.place.XmlPlaces;
import net.ramify.model.place.xml.place.group.XmlPlaceGroups;
import net.ramify.model.record.archive.ArchiveProvider;

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
        this.expose(PlaceProvider.class);
        this.expose(PlaceGroupProvider.class);
        this.expose(PositionProvider.class);
        this.expose(PlaceDescriptionProvider.class);
        this.expose(ArchiveProvider.class);
        this.expose(PlaceHierarchyProvider.class);
    }

    @Provides
    @Singleton
    JAXBContext providePlaceContext() throws JAXBException {
        return JAXBContext.newInstance(XmlPlaces.class, XmlPositions.class, XmlPlaceDescriptions.class, XmlArchives.class, XmlPlaceGroups.class);
    }

    @Provides
    @Singleton
    PlaceGroupProvider providePlaceGroupProvider(final JAXBContext context, @Named("data") final File data, final PlaceProvider placeProvider) throws JAXBException {
        return XmlPlaceGroupProvider.readGroupsInDirectory(context, data, placeProvider);
    }

    @Provides
    @Singleton
    PlaceProvider providePlaceProvider(
            final JAXBContext jaxbContext,
            @Named("data") final File data,
            final ParserContext context) throws JAXBException {
        return XmlPlaceProvider.readPlacesInCountryRoot(jaxbContext, data, context);
    }

    @Provides
    @Singleton
    PositionProvider providePositionProvider(final JAXBContext context, @Named("data") final File data) throws JAXBException {
        return XmlPositionProvider.readPositionsInDirectory(context, data);
    }

    @Provides
    @Singleton
    PlaceDescriptionProvider placeDescriptionProvider(final JAXBContext context, @Named("data") final File data, final PlaceProvider placeProvider) throws JAXBException {
        return XmlPlaceDescriptionProvider.readDescriptionsInDirectory(context, data, placeProvider);
    }

    @Provides
    @Singleton
    ArchiveProvider archiveProvider(final JAXBContext context, @Named("data") final File data) throws JAXBException {
        return XmlArchiveProvider.readArchivesInDirectory(context, data);
    }

    @Provides
    @Nonnull
    @Singleton
    @Named("data")
    File provideXmlDirectory() throws URISyntaxException {
        final var places = XmlPlaceModule.class.getResource("/xml/data/places");
        Preconditions.checkState(places != null, "Could not load XML data");
        return new File(places.toURI());
    }

    @Provides
    @Singleton
    PlaceHierarchyProvider hierarchyProvider() {
        return new XmlPlaceHierarchyProvider(HashMultimap.create()); //TODO
    }

}
