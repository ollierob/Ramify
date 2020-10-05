package net.ramify.model.family;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateRangeParser;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.EventId;
import net.ramify.model.event.PersonEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.place.provider.PlaceParser;

abstract class Gedcom55EventBuilder implements GedcomFamilyLineReader {

    private String type;
    private String date;
    private String place;

    @Override
    public void read(final int level, final int start, final String line) {
        if (level != 2) return;
        final var next = line.indexOf(' ', start);
        final var type = next > 0 ? line.substring(start, next) : line.substring(start);
        switch (type) {
            case "TYPE" -> this.type = line.substring(next + 1);
            case "DATE" -> this.date = line.substring(next + 1);
            case "PLAC" -> this.place = line.substring(next + 1);
        }
    }

    DateRange date(final DateRangeParser parser) {
        return parser.require(date);
    }

    Place place(final PlaceParser parser) {
        return parser.get(place);
    }

    String type() {
        return type;
    }

    EventBuilder eventBuilder(final DateRangeParser dateParser, final PlaceParser placeParser) {
        return EventBuilder.builder(EventId.random(), this.date(dateParser))
                .withPlace(this.place(placeParser));
    }

    abstract PersonEvent build(PersonId personId);

}
