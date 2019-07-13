package net.ramify.model.person;

import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@Singleton
public class XmlNameParser implements NameParser {

    @Nonnull
    @Override
    public Name parse(final String name) {
        throw new UnsupportedOperationException(); //TODO
    }

}
