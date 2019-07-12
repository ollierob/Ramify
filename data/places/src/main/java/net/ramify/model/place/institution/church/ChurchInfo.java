package net.ramify.model.place.institution.church;

import net.ramify.model.place.building.Church;
import net.ramify.model.place.institution.InstitutionInfo;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface ChurchInfo extends InstitutionInfo {

    @Nonnull
    Church place();

    @CheckForNull
    String denomination();

    @Override
    @Deprecated
    default String type() {
        return this.denomination();
    }

}
