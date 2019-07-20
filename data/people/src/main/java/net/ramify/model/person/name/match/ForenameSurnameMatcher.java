package net.ramify.model.person.name.match;

import net.ramify.model.person.name.ForenameSurname;
import net.ramify.strategy.match.name.NameMatch;

public class ForenameSurnameMatcher implements NameMatch<ForenameSurname> {

    @Override
    public boolean match(final ForenameSurname n1, final ForenameSurname n2) {
        return this.matchForename(n1, n2) && this.matchSurname(n1, n2);
    }

    private boolean matchForename(final ForenameSurname n1, final ForenameSurname n2) {
        return true; //TODO
    }

    private boolean matchSurname(final ForenameSurname n1, final ForenameSurname n2) {
        final var s1 = n1.surname().toLowerCase();
        final var s2 = n2.surname().toLowerCase();
        return s1.length() > s2.length() ? s1.contains(s2) : s2.contains(s1);
    }

}
