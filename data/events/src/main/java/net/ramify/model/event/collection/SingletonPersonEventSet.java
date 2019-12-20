package net.ramify.model.event.collection;

import com.google.common.collect.Iterators;
import net.ramify.model.event.PersonEvent;

import javax.annotation.Nonnull;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.function.Predicate;

public class SingletonPersonEventSet extends AbstractSet<PersonEvent> implements PersonEventSet {

    private final PersonEvent event;

    public SingletonPersonEventSet(final PersonEvent event) {
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
    public Iterator<PersonEvent> iterator() {
        return Iterators.singletonIterator(event);
    }

    @Nonnull
    @Override
    public PersonEventSet filteredCopy(@Nonnull Predicate<? super PersonEvent> predicate) {
        return predicate.test(event) ? this : EmptyPersonEventSet.INSTANCE;
    }

}
