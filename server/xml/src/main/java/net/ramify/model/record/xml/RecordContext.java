package net.ramify.model.record.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.archive.ArchiveProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@Singleton
public class RecordContext extends ParserContext {

    private final PlaceProvider places;
    private final ArchiveProvider archives;

    @Inject
    protected RecordContext(
            final NameParser nameParser,
            final DateParser dateParser,
            final PlaceProvider places,
            final ArchiveProvider archives) {
        super(nameParser, dateParser);
        this.places = places;
        this.archives = archives;
    }

    public PlaceProvider places() {
        return places;
    }

    public ArchiveProvider archives() {
        return archives;
    }

}
