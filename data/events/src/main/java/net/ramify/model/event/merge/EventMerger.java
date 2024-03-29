package net.ramify.model.event.merge;

import net.ramify.model.event.PersonEvent;
import net.ramify.model.strategy.MergeResult;
import net.ramify.model.strategy.Merger;

public interface EventMerger<E extends PersonEvent> extends Merger<E, E> {

    @Override
    default E just(final E event) {
        return event;
    }

    static <E extends PersonEvent> EventMerger<E> useNonInferred(final EventMerger<E> delegate) {
        return (e1, e2) -> {
            if (e1 == null) return MergeResult.ofNullable(e2);
            if (e2 == null) return MergeResult.of(e1);
            if (e1.isInferred() == e2.isInferred()) return delegate.merge(e1, e2);
            if (e1.isInferred()) return MergeResult.of(e2);
            assert !e1.isInferred();
            return MergeResult.of(e1);
        };
    }

    static <E extends PersonEvent> EventMerger<E> useRight() {
        return (e1, e2) -> MergeResult.of(e2);
    }

    static <E extends PersonEvent> EventMerger<E> notPermitted() {
        return (e1, e2) -> {
            if (e1 == null) return MergeResult.ofNullable(e2);
            if (e2 == null) return MergeResult.of(e1);
            throw new IllegalArgumentException(String.format("Merge not permitted: %s and %s", e1, e2));
        };
    }

}
