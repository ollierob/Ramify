package net.ramify.model.place.institution.church;

import net.ramify.model.place.building.Church;
import net.ramify.model.place.institution.InstitutionInfo;
import net.ramify.model.place.institution.church.record.ChurchRecordSetInfo;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

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

    @Nonnull
    Set<? extends ChurchRecordSetInfo> records();

}
