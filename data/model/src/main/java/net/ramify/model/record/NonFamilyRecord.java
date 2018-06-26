package net.ramify.model.record;

import net.ramify.model.family.Families;

import javax.annotation.Nonnull;

public interface NonFamilyRecord extends Record {

    @Nonnull
    @Override
    @Deprecated
    default Families families() {
        return Families.of();
    }

}
