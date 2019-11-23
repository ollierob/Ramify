package net.ramify.model.person.name;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ChangedName implements Name {

    private final Name before;
    private final Name after;

    public ChangedName(@Nonnull final Name before, @Nonnull final Name after) {
        this.before = Objects.requireNonNull(before);
        this.after = Objects.requireNonNull(after);
    }

    public Name before() {
        return before;
    }

    public Name after() {
        return after;
    }

    @Nonnull
    @Override
    public String value() {
        return after.value();
    }

}
