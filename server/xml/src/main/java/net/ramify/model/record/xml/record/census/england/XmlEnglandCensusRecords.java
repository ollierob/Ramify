package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.census.XmlCensusRecords;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "englandCensus")
public class XmlEnglandCensusRecords extends XmlCensusRecords {

    @XmlElements({
            @XmlElement(name = "entry1821byAge", type = XmlEngland1821ByAgeCensusRecord.class, namespace = XmlRecord.NAMESPACE),
            @XmlElement(name = "entry1841", type = XmlEngland1841CensusRecord.class, namespace = XmlRecord.NAMESPACE),
            @XmlElement(name = "entry1851", type = XmlEngland1851CensusRecord.class, namespace = XmlRecord.NAMESPACE)
    })
    private List<XmlEnglandCensus> records;

    @Override
    protected List<XmlEnglandCensus> records() {
        return records;
    }

}
