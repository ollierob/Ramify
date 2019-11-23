package net.ramify.strategy.event.merge;

import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.strategy.merge.Merger;

import javax.annotation.Nonnull;

public class BirthMerger implements EventMerger<Birth> {

    @Nonnull
    @Override
    public Merger.Result<Birth> merge(final Birth b1, final Birth b2) {
        return b1.date().intersection(b2.date())
                .map(d -> Merger.<Birth>value(new GenericBirth(b1.personId(), d)))
                .orElse(Merger.impossible());
    }

}
