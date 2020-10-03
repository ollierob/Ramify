package net.ramify.model.event.historic;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.age.Age;
import net.ramify.model.place.type.SettlementOrRegion;
import net.ramify.model.util.Link;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class HistoricEventWithAge implements HistoricEvent {

    private final HistoricEvent event;
    private final Age age;

    public HistoricEventWithAge(final HistoricEvent event, final Age age) {
        this.event = event;
        this.age = age;
    }

    @CheckForNull
    @Override
    public SettlementOrRegion region() {
        return event.region();
    }

    @Nonnull
    @Override
    public Set<Link> links() {
        return event.links();
    }

    @Nonnull
    @Override
    public String title() {
        return event.title();
    }

    @Nonnull
    @Override
    public Optional<String> description() {
        return event.description();
    }

    @Nonnull
    @Override
    public DateRange date() {
        return event.date();
    }

    @Nonnull
    @Override
    public EventId eventId() {
        return event.eventId();
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return event.toProtoBuilder()
                .setComputedAge(age.toProto());
    }

}
