package net.ramify.model.record;

import net.ramify.model.family.Families;
import net.ramify.model.family.Family;

import javax.annotation.Nonnull;

public interface SingleFamilyRecord extends Record {

    Family family();

    @Nonnull
    @Override
    default Families families() {
        return Families.of(this.family());
    }

}
