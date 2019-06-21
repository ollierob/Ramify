package net.ramify.model.person.name;

import java.util.Collections;
import java.util.List;

public class ForenameSurname implements Name {

    private final String forename;
    private final List<String> middleNames;
    private final String surname;

    public ForenameSurname(final String forename, final String surname) {
        this(forename, Collections.emptyList(), surname);
    }

    public ForenameSurname(final String forename, final List<String> middleNames, final String surname) {
        this.forename = forename;
        this.middleNames = middleNames;
        this.surname = surname;
    }

    public String forname() {
        return forename;
    }

    public String surname() {
        return surname;
    }

    @Override
    public String value() {
        return (forename + ' ' + surname).trim();
    }

}
