package net.ramify.model.record.xml.record.census;

import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.ListUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "censusRecords")
public class XmlCensusRecords extends XmlRecords {

    @XmlAttribute(name = "censusPlace", required = true)
    private String censusPlace;

    @XmlElementRef
    private List<XmlCensusRecord> records;

    @Override
    public int size() {
        if (records == null) return 0;
        return records.stream().mapToInt(XmlCensusRecord::size).sum();
    }

    @Override
    public Collection<? extends CensusRecord> build(final RecordSet recordSet, final RecordContext context) {
        if (records == null) return Collections.emptyList();
        final var censusPlace = context.places().require(new PlaceId(this.censusPlace));
        return ListUtils.eagerlyTransform(records, record -> record.build(context, censusPlace, recordSet));
    }

}
