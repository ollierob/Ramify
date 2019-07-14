package net.ramify.model.place.xml.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.institution.church.ChurchInfo;
import net.ramify.model.record.collection.RecordSet;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;
import java.util.Set;

@XmlTransient
public class ResolvedChurchInfo implements ChurchInfo {

    private final Church church;
    private final String denomination;
    private final DateRange founded;
    private final DateRange closed;
    private final Set<RecordSet> records;

    ResolvedChurchInfo(
            final Church church,
            final String denomination,
            final DateRange founded,
            final DateRange closed,
            final Set<RecordSet> records) {
        this.church = Objects.requireNonNull(church);
        this.denomination = denomination;
        this.founded = founded;
        this.closed = closed;
        this.records = records;
    }

    @Nonnull
    @Override
    public Church place() {
        return church;
    }

    @CheckForNull
    @Override
    public String denomination() {
        return denomination;
    }

    @Nonnull
    @Override
    public DateRange founded() {
        return founded;
    }

    @CheckForNull
    @Override
    public DateRange closed() {
        return closed;
    }

    @Nonnull
    @Override
    public Set<RecordSet> records() {
        return records;
    }

}
