package net.ramify.strategy.person.merge.name;

import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.TranscribedName;

import javax.annotation.Nonnull;
import java.util.Optional;

public class DefaultNameMerger implements NameMerger<Name> {

    private final ForenameSurnameMerger forenameSurnameMerger;

    public DefaultNameMerger(final ForenameSurnameMerger forenameSurnameMerger) {
        this.forenameSurnameMerger = forenameSurnameMerger;
    }

    @Nonnull
    @Override
    public Result<Name> merge(final Name n1, final Name n2) {
        return forenameSurnameMerger.merge(readForenameSurname(n1), readForenameSurname(n2));
    }

    private static Optional<ForenameSurname> readForenameSurname(final Name name) {
        if (name instanceof ForenameSurname) return Optional.of((ForenameSurname) name);
        if (name instanceof TranscribedName) return readForenameSurname(((TranscribedName) name).assumed());
        return Optional.empty();
    }

}
