package net.ramify.model;

import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.name.NameParser;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;

@Singleton
@XmlTransient
public class ParserContext {

    private final NameParser nameParser;
    private final DateParser dateParser;

    @Inject
    protected ParserContext(final NameParser nameParser, final DateParser dateParser) {
        this.nameParser = nameParser;
        this.dateParser = dateParser;
    }

    public NameParser nameParser() {
        return nameParser;
    }

    public DateParser dateParser() {
        return dateParser;
    }
}
