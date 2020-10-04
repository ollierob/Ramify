package net.ramify.model.family;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.DateRangeParser;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.EventId;
import net.ramify.model.event.PersonEvent;
import net.ramify.model.person.PersonId;

abstract class Gedcom55EventBuilder implements GedcomFamilyLineReader {

    private String date;
    private String place;

    @Override
    public void read(final int level, final int start, final String line) {
        if (level != 2) return;
        final var next = line.indexOf(' ', start);
        final var type = next > 0 ? line.substring(start, next) : line.substring(start);
        switch (type) {
            case "DATE" -> this.date = line.substring(next + 1);
            case "PLAC" -> this.place = line.substring(next + 1);
        }
    }

    DateRange date() {
        return DateRangeParser.parse(date).orElseThrow(() -> new UnsupportedOperationException("Could not parse date: " + date));
    }

    EventBuilder eventBuilder() {
        return EventBuilder.builder(EventId.random(), this.date());
    }

    abstract PersonEvent build(PersonId personId);

}
