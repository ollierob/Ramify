package net.ramify.model.person.name.match;

import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.person.name.Name;
import net.ramify.strategy.match.name.NameMatch;

public class ComposedNameMatcher implements NameMatch<Name> {

    private final NameMatch<ForenameSurname> forenameSurnameNameMatch;

    public ComposedNameMatcher(final NameMatch<ForenameSurname> forenameSurnameNameMatch) {
        this.forenameSurnameNameMatch = forenameSurnameNameMatch;
    }

    @Override
    public boolean match(final Name n1, final Name n2) {
        if (n1 instanceof ForenameSurname && n2 instanceof ForenameSurname) {
            return forenameSurnameNameMatch.match((ForenameSurname) n1, (ForenameSurname) n2);
        }
        return this.defaultMatch(n1, n2);
    }

    private boolean defaultMatch(final Name n1, final Name n2) {
        final var s1 = n1.value().toLowerCase();
        final var s2 = n2.value().toLowerCase();
        return s1.length() > s2.length() ? s1.contains(s2) : s2.contains(s1);
    }

}
