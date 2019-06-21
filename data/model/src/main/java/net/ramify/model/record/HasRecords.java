package net.ramify.model.record;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasRecords {

    @Nonnull
    Set<Record> records();

}
