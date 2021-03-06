package net.ramify.model.record.xml.record.census;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
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

    @XmlAttribute(name = "censusPlace")
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
        final var censusArea = this.censusArea(recordSet, context.places());
        return ListUtils.eagerlyTransform(records, record -> record.build(context, censusArea, recordSet));
    }

    Place censusArea(final RecordSet recordSet, final PlaceProvider places) {
        if (censusArea != null) return places.require(new PlaceId(censusArea));
        return recordSet.covers().resolvePlace(places);
    }

    @Nonnull
    protected abstract List<? extends XmlCensusRecord> records();

}
