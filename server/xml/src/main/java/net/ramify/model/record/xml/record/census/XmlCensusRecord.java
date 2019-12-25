package net.ramify.model.record.xml.record.census;

import net.ramify.model.place.Place;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPlaceRecord;
import net.ramify.model.record.xml.record.census.england.XmlEngland1821ByAgeCensusRecord;
import net.ramify.model.record.xml.record.census.england.XmlEngland1841CensusRecord;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({XmlEngland1821ByAgeCensusRecord.class, XmlEngland1841CensusRecord.class})
public abstract class XmlCensusRecord extends XmlPlaceRecord {

    protected abstract CensusRecord build(RecordContext context, Place censusPlace, RecordSet recordSet);

}
