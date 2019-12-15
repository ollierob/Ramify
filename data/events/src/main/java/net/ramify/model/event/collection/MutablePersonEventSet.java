package net.ramify.model.event.collection;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.event.type.PostDeathEvent;
import net.ramify.model.event.type.UniqueEvent;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class MutablePersonEventSet extends HashSet<Event> implements PersonEventSet {

    private final Handler handler = new Handler();

    public MutablePersonEventSet() {
    }

    public MutablePersonEventSet(final Event... events) {
        for (int i = 0; i < events.length; i++) {
            this.addToHandler(events[i]);
        }
    }

    public MutablePersonEventSet(final Collection<? extends Event> events) {
        events.forEach(this::addToHandler);
    }

    @Override
    public boolean add(final Event event) {
        return this.addToHandler(event);
    }

    private boolean addToHandler(final Event event) {
        return event != null && handler.handle(event);
    }

    private boolean addToSet(final Event event) {
        return super.add(event);
    }

    @Override
    public boolean remove(final Object event) {
        return event != null && super.remove(event);
    }

    @Nonnull
    @Override
    public <T extends UniqueEvent> Optional<T> find(final Class<T> type) {
        if (BirthEvent.class.isAssignableFrom(type)) return this.findBirth().flatMap(b -> b.as(type));
        if (DeathEvent.class.isAssignableFrom(type)) return this.findDeath().flatMap(b -> b.as(type));
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    @Override
    public Optional<BirthEvent> findBirth() {
        return Optional.ofNullable(handler.birth);
    }

    @Nonnull
    @Override
    public Optional<DeathEvent> findDeath() {
        return Optional.ofNullable(handler.death);
    }

    private final class Handler implements EventHandler<Boolean> {

        private BirthEvent birth;
        private DeathEvent death;

        @Override
        public Boolean handle(final BirthEvent event) {
            final var birth = this.birth = this.merge(this.birth, event);
            return addToSet(birth);
        }

        private BirthEvent merge(final BirthEvent prev, final BirthEvent next) {
            //TODO
            remove(prev);
            return next;
        }

        @Override
        public Boolean handle(final LifeEvent event) {
            return this.add(event);
        }

        @Override
        public Boolean handle(final DeathEvent event) {
            final var death = this.death = this.merge(this.death, event);
            return addToSet(death);
        }

        private DeathEvent merge(final DeathEvent prev, final DeathEvent next) {
            //TODO
            remove(prev);
            return next;
        }

        @Override
        public Boolean handle(final PostDeathEvent event) {
            return this.add(event);
        }

        private boolean add(final Event event) {
            return event instanceof UniqueEvent
                    ? this.addUnique((UniqueEvent) event)
                    : addToSet(event);
        }

        private boolean addUnique(final UniqueEvent event) {
            return addToSet(event); //FIXME
        }

    }

}