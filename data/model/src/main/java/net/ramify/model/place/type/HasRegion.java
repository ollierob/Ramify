package net.ramify.model.place.type;

import javax.annotation.Nonnull;

public interface HasRegion extends HasCountry {

    @Nonnull
    Region region();

    @Override
    default Country country() {
        return this.region().country();
    }

}
