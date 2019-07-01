package net.ramify.server.resource.places.churches;

import com.google.common.collect.Collections2;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.PlaceIds;
import net.ramify.model.place.institution.church.ChurchInfo;
import net.ramify.model.place.institution.church.ChurchInfoProvider;
import net.ramify.server.resource.places.ChurchesResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultChurchesResource implements ChurchesResource {

    private final ChurchInfoProvider churchInfoProvider;

    @Inject
    DefaultChurchesResource(final ChurchInfoProvider churchInfoProvider) {
        this.churchInfoProvider = churchInfoProvider;
    }

    @Override
    public ChurchInfo info(final PlaceId churchId) {
        return churchInfoProvider.get(churchId);
    }

    @Override
    public PlaceIds inRegion(final PlaceId region) {
        final var infos = churchInfoProvider.within(region);
        return PlaceIds.of(Collections2.transform(infos, ChurchInfo::placeId));
    }

}
