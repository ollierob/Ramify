package net.ramify.model.place.xml.settlements;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.church.Church;
import net.ramify.model.place.type.Building;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class ResolvedChurch implements Church {

    private final XmlChurch xml;
    private final Building building;
    private final DateParser dateParser;

    ResolvedChurch(final XmlChurch xml, final Building building, final DateParser dateParser) {
        this.xml = xml;
        this.building = building;
        this.dateParser = dateParser;
    }

    @Nonnull
    @Override
    public Building place() {
        return building;
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
