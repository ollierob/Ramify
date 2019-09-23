package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.place.Place;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.census.XmlCensusRecord;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecord;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlRecord.NAMESPACE, name = "censusEngland1851")
public class XmlEngland1851CensusRecord extends XmlCensusRecord {

    @XmlElementRef
    private XmlResidenceRecord head;

    @Override
    protected int numIndividuals() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    protected CensusRecord build(final RecordContext context, final Place censusPlace, final RecordSet recordSet) {
        throw new UnsupportedOperationException(); //TODO
    }

}
