package net.ramify.strategy.match;

import javax.annotation.Nonnull;

public interface Match<T> {

    boolean match(@Nonnull T t1, @Nonnull T t2);

}
