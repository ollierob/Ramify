package net.ramify.model.record.provider;

import net.ramify.model.util.provider.Provider;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface RecordsProvider extends Provider<RecordSetId, Records> {

    @Nonnull
    Collection<Records> all();

    @Nonnull
    RecordsByPlaceIndex placeIndex();

}
