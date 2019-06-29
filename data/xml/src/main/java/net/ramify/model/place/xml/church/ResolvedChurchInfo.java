package net.ramify.model.place.xml.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.church.ChurchInfo;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class ResolvedChurchInfo implements ChurchInfo {

    private final XmlChurchInfo xml;
    private final Church church;
    private final DateParser dateParser;

    ResolvedChurchInfo(final XmlChurchInfo xml, final Church church, final DateParser dateParser) {
        this.xml = xml;
        this.church = church;
        this.dateParser = dateParser;
    }

    @Nonnull
    @Override
    public Church place() {
        return church;
    }

    @CheckForNull
    @Override
    public String denomination() {
        return xml.denomination();
    }

    @Nonnull
    @Override
    public DateRange founded() {
        return xml.founded(dateParser);
    }

    @CheckForNull
    @Override
    public DateRange closed() {
        return xml.closed();
    }

}
