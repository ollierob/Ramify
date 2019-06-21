package net.ramify.model.person.name;

import java.util.List;

public class Initials implements Name {

    private final List<Character> initials;

    public Initials(final List<Character> initials) {
        this.initials = initials;
    }

    @Override
    public String value() {
        throw new UnsupportedOperationException(); //TODO
    }

}
