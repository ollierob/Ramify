package net.ramify.model.record;

import net.ramify.model.family.Family;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface SingleFamilyRecord extends Record {

    @Nonnull
    Family family();

    @Deprecated
    @Override
    default Set<? extends Family> families() {
        return Collections.singleton(this.family());
    }

}
