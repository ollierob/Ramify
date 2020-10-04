package net.ramify.model.event.collection;

import net.ramify.model.event.PersonEvent;

import javax.annotation.Nonnull;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

public class NoPersonEvents extends AbstractSet<PersonEvent> implements PersonEventSet {

    public static final NoPersonEvents INSTANCE = new NoPersonEvents();

    private NoPersonEvents() {
    }

    @Override
    public Iterator<PersonEvent> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public int size() {
        return 0;
    }

    @Nonnull
    @Override
    public PersonEventSet filteredCopy(@Nonnull final Predicate<? super PersonEvent> predicate) {
        return this;
    }

}
