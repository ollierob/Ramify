package net.ramify.model.event.merge;

import net.ramify.model.event.Event;
import net.ramify.model.strategy.Merger;

public interface EventMerger<E extends Event> extends Merger<E, E> {

    @Override
    default E just(final E event) {
        return event;
    }

    static <E extends Event> EventMerger<E> useNonInferred(final EventMerger<E> delegate) {
        return (e1, e2) -> {
            if (e1 == null) return Result.ofNullable(e2);
            if (e2 == null) return Result.of(e1);
            if (e1.isInferred() == e2.isInferred()) return delegate.merge(e1, e2);
            if (e1.isInferred()) return Result.of(e2);
            assert !e1.isInferred();
            return Result.of(e1);
        };
    }

    static <E extends Event> EventMerger<E> useRight() {
        return (e1, e2) -> Result.of(e2);
    }

    static <E extends Event> EventMerger<E> notPermitted() {
        return (e1, e2) -> {
            if (e1 == null) return Result.ofNullable(e2);
            if (e2 == null) return Result.of(e1);
            throw new IllegalArgumentException(String.format("Merge not permitted: %s and %s", e1, e2));
        };
    }

}
