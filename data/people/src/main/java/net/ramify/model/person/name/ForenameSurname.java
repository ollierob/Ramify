package net.ramify.model.person.name;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static net.ramify.utils.StringUtils.isBlank;

public record ForenameSurname(String prefix, List<String>forenames, String surname, String suffix) implements Name {

    private static final Joiner JOIN_SPACES = Joiner.on(' ');
    private static final Splitter SPLIT_SPACES = Splitter.on(Pattern.compile("\\s+"));

    public ForenameSurname(final String forename, final String surname) {
        this(null, forename == null || forename.isBlank() ? Collections.emptyList() : SPLIT_SPACES.splitToList(forename), surname, null);
    }

    @Nonnull
    public List<Character> initials(final boolean includeSurname) {
        final var initials = Lists.<Character>newArrayList();
        forenames.forEach(f -> initials.add(Character.toUpperCase(f.charAt(0))));
        if (includeSurname && !surname.isEmpty()) initials.add(surname.charAt(0));
        return initials;
    }

    @Nonnull
    @Override
    public String value() {
        final var sb = new StringBuilder();
        append(prefix, sb);
        if (!forenames.isEmpty()) append(JOIN_SPACES.join(forenames), sb);
        append(surname, sb);
        append(suffix, sb);
        return sb.toString().trim();
    }

    private static void append(final String part, final StringBuilder sb) {
        if (isBlank(part)) return;
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') sb.append(' ');
        sb.append(part).append(' ');
    }

    @Override
    public String toString() {
        return this.value();
    }

}
