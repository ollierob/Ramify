package net.ramify.model.family;

import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.StringName;

import static net.ramify.utils.StringUtils.isEmpty;

class GedcomParser {

    static PersonId personId(final String id) {
        if (id.startsWith("@") && id.endsWith("@")) return new PersonId(id.substring(1, id.length() - 1));
        return new PersonId(id);
    }

    static Gender parseSex(final String sex) {
        return switch (sex) {
            case "M" -> Gender.MALE;
            case "F" -> Gender.FEMALE;
            default -> Gender.UNKNOWN;
        };
    }

    static Name parseName(final String name) {
        if (isEmpty(name)) return Name.UNKNOWN;
        if (name.endsWith("/")) {
            final var f = name.indexOf('/');
            return new ForenameSurname(name.substring(0, f - 1), name.substring(f + 1, name.length() - 1));
        }
        return new StringName(name);
    }

}
