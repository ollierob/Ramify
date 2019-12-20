package net.ramify.model.event.collection;

import com.google.common.collect.Iterators;
import net.ramify.model.event.Event;

import javax.annotation.Nonnull;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.function.Predicate;

public class SingletonPersonEventSet extends AbstractSet<Event> implements PersonEventSet {

    private final Event event;

    public SingletonPersonEventSet(final Event event) {
        this.event = event;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<Event> iterator() {
        return Iterators.singletonIterator(event);
    }

    @Nonnull
    @Override
    public PersonEventSet filteredCopy(@Nonnull Predicate<? super Event> predicate) {
        return predicate.test(event) ? this : EmptyPersonEventSet.INSTANCE;
    }

}
