package net.ramify.model;

import net.ramify.model.family.Families;
import net.ramify.model.family.Family;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;

public interface SingleFamilyRecord extends Record {

    Family family();

    @Nonnull
    @Override
    default Families families() {
        return Families.of(this.family());
    }

}
