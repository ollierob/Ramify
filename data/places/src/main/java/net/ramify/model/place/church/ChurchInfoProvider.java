package net.ramify.model.place.church;

import net.ramify.model.Provider;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;
import java.util.Set;

public interface ChurchInfoProvider extends Provider<PlaceId, ChurchInfo> {

    @Nonnull
    Set<ChurchInfo> within(PlaceId region);

}
