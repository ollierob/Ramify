package net.ramify.model.record.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.archive.ArchiveProvider;

import javax.annotation.CheckForNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@Singleton
public class RecordContext extends ParserContext {

    private final PlaceProvider places;
    private final ArchiveProvider archives;
    private final DateRange recordDate;

    @Inject
    protected RecordContext(
            final NameParser nameParser,
            final DateParser dateParser,
            final PlaceProvider places,
            final ArchiveProvider archives) {
        this(nameParser, dateParser, places, archives, null);
    }

    public RecordContext(
            final NameParser nameParser,
            final DateParser dateParser,
            final PlaceProvider places,
            final ArchiveProvider archives,
            final DateRange recordDate) {
        super(nameParser, dateParser);
        this.places = places;
        this.archives = archives;
        this.recordDate = recordDate;
    }

    public PlaceProvider places() {
        return places;
    }

    public ArchiveProvider archives() {
        return archives;
    }

    @CheckForNull
    public DateRange recordDate() {
        return recordDate;
    }

    public RecordContext onDate(final DateRange date) {
        return new RecordContext(nameParser(), dateParser(), places, archives, date);
    }

}
