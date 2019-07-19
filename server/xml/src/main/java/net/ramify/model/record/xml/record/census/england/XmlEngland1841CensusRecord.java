package net.ramify.model.record.xml.record.census.england;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.region.County;
import net.ramify.model.record.residence.uk.Census1841Record;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.census.XmlCensusRecord;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "census1841England")
public class XmlEngland1841CensusRecord extends XmlCensusRecord {

    @XmlElement(type = Individual.class, name = "entry", namespace = XmlRecord.NAMESPACE)
    private List<Individual> individuals;

    @Override
    protected Census1841Record build(final RecordContext context, final Place censusPlace) {
        final var id = this.recordId();
        final var place = this.place(context.places());
        final var entries = this.entries(context, censusPlace);
        return new Census1841Record(id, place, entries);
    }

    List<Census1841Record.Census1841Entry> entries(final RecordContext context, final Place censusPlace) {
        return ListUtils.eagerlyTransform(individuals, entry -> entry.build(context, censusPlace));
    }

    @Override
    protected int size() {
        return individuals.size();
    }

    @XmlType(namespace = XmlRecord.NAMESPACE, name = "census1841EnglandEntry")
    public static class Individual extends XmlPersonRecord {

        @XmlAttribute(name = "bornInCounty")
        private Boolean bornInCounty;

        @Nonnull
        Census1841Record.Census1841Entry build(final RecordContext context, final Place censusPlace) {
            return new Census1841Record.Census1841Entry(
                    this.personId(),
                    this.name(context.nameParser()),
                    this.age(),
                    this.gender(),
                    this.birthPlace(censusPlace));
        }

        Place birthPlace(final Place place) {
            final var bornInCounty = MoreObjects.firstNonNull(this.bornInCounty, true);
            if (bornInCounty) return place.find(County.class).get();
            return null;
        }

    }

}