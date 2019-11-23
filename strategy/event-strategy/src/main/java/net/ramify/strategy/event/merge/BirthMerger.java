package net.ramify.strategy.event.merge;

import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.birth.GenericBirth;

import javax.annotation.Nonnull;
import java.util.Optional;

public class BirthMerger implements EventMerger<Birth> {

    @Nonnull
    @Override
    public Optional<Birth> merge(final Birth b1, final Birth b2) {
        return b1.date().intersection(b2.date()).map(d -> new GenericBirth(b1.personId(), d));
    }

}
