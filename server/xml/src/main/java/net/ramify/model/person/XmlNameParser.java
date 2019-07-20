package net.ramify.model.person;

import com.google.common.base.Splitter;
import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;
import java.util.regex.Pattern;

@XmlTransient
@Singleton
public class XmlNameParser implements NameParser {

    private static final Splitter SPLIT_SPACE = Splitter.on(Pattern.compile("\\s+"));

    @Nonnull
    @Override
    public Name parse(String name) {
        if (name.indexOf(',') >= 0) throw new UnsupportedOperationException();
        name = name.trim();
        final var split = SPLIT_SPACE.splitToList(name);
        switch (split.size()) {
            case 1:
                if (name.length() <= 2) return new ForenameSurname(name, null);
                return new ForenameSurname(null, name);
            case 2:
                return new ForenameSurname(split.get(0), split.get(1));
            default:
                return new ForenameSurname(null, split.subList(0, split.size() - 1), split.get(split.size() - 1), null);
        }
    }

}
