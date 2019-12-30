package net.ramify.model.record.xml.record.census.england;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.census.XmlCensusRecord;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({XmlEngland1821ByAgeCensusRecord.class, XmlEngland1841CensusRecord.class, XmlEngland1851CensusRecord.class, XmlEngland1939RegisterRecord.class})
abstract class XmlEnglandCensusRecord extends XmlCensusRecord {

    protected Place place(final RecordContext context, final Place censusPlace) {
        return MoreObjects.firstNonNull(super.place(context.places()), censusPlace);
    }

}
