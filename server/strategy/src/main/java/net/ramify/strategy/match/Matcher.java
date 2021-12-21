package net.ramify.strategy.match;

import javax.annotation.Nonnull;

public interface Matcher<T> {

    @Nonnull
    MatchResult match(T t1, T t2);

}
