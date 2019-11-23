package net.ramify.model.record.xml.record.marriage;

import net.ramify.model.record.type.MarriageRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "marriage")
public class XmlMarriageRecord extends XmlRecord {

    public int numIndividuals() {
        throw new UnsupportedOperationException();
    }

    public MarriageRecord toRecord(final RecordContext context) {
        throw new UnsupportedOperationException();
    }

}
