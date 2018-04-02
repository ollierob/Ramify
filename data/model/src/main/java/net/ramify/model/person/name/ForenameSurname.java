package net.ramify.model.person.name;

import java.util.List;

public class ForenameSurname implements Name {

    private final String forename;
    private final List<String> middleNames;
    private final String surname;

    public ForenameSurname(final String forename, final List<String> middleNames, final String surname) {
        this.forename = forename;
        this.middleNames = middleNames;
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForenameSurname that = (ForenameSurname) o;

        if (!forename.equals(that.forename)) return false;
        if (!middleNames.equals(that.middleNames)) return false;
        return surname.equals(that.surname);
    }

    @Override
    public int hashCode() {
        int result = forename.hashCode();
        result = 31 * result + middleNames.hashCode();
        result = 31 * result + surname.hashCode();
        return result;
    }
}
