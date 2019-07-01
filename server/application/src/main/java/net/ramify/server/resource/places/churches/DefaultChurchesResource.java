package net.ramify.server.resource.places.churches;

import com.google.common.collect.Collections2;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.institution.church.ChurchInfo;
import net.ramify.model.place.institution.church.ChurchInfoProvider;
import net.ramify.server.resource.places.ChurchesResource;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

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
    public Collection<PlaceId> inRegion(final PlaceId region) {
        final var infos = churchInfoProvider.within(region);
        return Collections2.transform(infos, ChurchInfo::placeId);
    }

}
