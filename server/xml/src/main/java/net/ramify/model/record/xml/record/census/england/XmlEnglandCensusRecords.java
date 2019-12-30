package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.record.xml.record.census.XmlCensusRecords;
import net.ramify.model.record.xml.record.country.uk.XmlUkRecord;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(namespace = XmlUkRecord.NAMESPACE, name = "englandCensus")
public class XmlEnglandCensusRecords extends XmlCensusRecords {

    @XmlElementRef
    private List<XmlEnglandCensusRecord> records;

    @Override
    protected List<XmlEnglandCensusRecord> records() {
        return records;
    }

}
