package net.ramify.model.event.historic;

import com.google.common.collect.Iterables;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.age.Age;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public interface HistoricEvents extends Iterable<HistoricEvent>, BuildsProto<EventProto.EventList> {

    @Nonnull
    Stream<HistoricEvent> events();

    @Override
    default Iterator<HistoricEvent> iterator() {
        return this.events().iterator();
    }

    @Nonnull
    @Override
    default EventProto.EventList toProto() {
        return EventProto.EventList.newBuilder()
                .addAllEvent(Iterables.transform(this, Event::toProto))
                .build();
    }

    static HistoricEvents of(final Collection<HistoricEvent> events, final LocalDate computeAgeFrom) {
        final var range = DateRange.of(computeAgeFrom);
        return new HistoricEvents() {

            @Nonnull
            @Override
            public Stream<HistoricEvent> events() {
                return events.stream().map(event -> new HistoricEventWithAge(event, Age.fromDates(range, event.date())));
            }

        };
    }

}
