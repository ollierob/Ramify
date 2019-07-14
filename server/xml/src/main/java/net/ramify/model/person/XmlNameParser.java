package net.ramify.model.person;

import com.google.common.base.Splitter;
import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@Singleton
public class XmlNameParser implements NameParser {

    private static final Splitter SPLIT_SPACE = Splitter.on(' ');

    @Nonnull
    @Override
    public Name parse(final String name) {
        if (name.indexOf(',') >= 0) throw new UnsupportedOperationException();
        final var split = SPLIT_SPACE.splitToList(name);
        if (split.size() >= 2) return new ForenameSurname(null, split.subList(0, split.size() - 1), split.get(split.size() - 1), null);
        throw new UnsupportedOperationException();
    }

}
