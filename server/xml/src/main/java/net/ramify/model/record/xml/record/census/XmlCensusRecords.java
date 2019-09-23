package net.ramify.model.record.xml.record.census;

import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.model.record.xml.record.census.england.XmlEnglandCensusRecords;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlSeeAlso({XmlEnglandCensusRecords.class})
public abstract class XmlCensusRecords extends XmlRecords {

    @XmlAttribute(name = "censusPlace", required = true)
    private String censusArea;

    @Override
    public int numRecords() {
        return this.records().size();
    }

    @Override
    public int numIndividuals() {
        return this.records().stream().mapToInt(XmlCensusRecord::numIndividuals).sum();
    }

    @Override
    public Collection<? extends CensusRecord> build(final RecordSet recordSet, final RecordContext context) {
        final var records = this.records();
        if (records.isEmpty()) return Collections.emptyList();
        final var censusArea = context.places().require(new PlaceId(this.censusArea));
        return ListUtils.eagerlyTransform(records, record -> record.build(context, censusArea, recordSet));
    }

    @Nonnull
    protected abstract List<XmlCensusRecord> records();

}
