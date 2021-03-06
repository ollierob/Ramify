package net.ramify.model.event.collection;

import net.ramify.model.event.PersonEvent;

import javax.annotation.Nonnull;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

class EmptyPersonEventSet extends AbstractSet<PersonEvent> implements PersonEventSet {

    static final EmptyPersonEventSet INSTANCE = new EmptyPersonEventSet();

    private EmptyPersonEventSet() {

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
    @Deprecated
    public PersonEventSet filteredCopy(final Predicate<? super PersonEvent> predicate) {
        return this;
    }

}
