package net.ramify.model.family;

import net.ramify.model.event.Event;

abstract class Gedcom55EventBuilder implements GedcomFamilyLineReader {

    @Override
    public void read(final int level, final int start, final String line) {
        throw new UnsupportedOperationException(); //TODO
    }

    abstract Event build();

}
