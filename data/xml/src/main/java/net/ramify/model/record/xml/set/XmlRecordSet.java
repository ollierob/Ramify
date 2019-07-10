package net.ramify.model.record.xml.set;

import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "recordSet")
class XmlRecordSet {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "title", required = true)
    private String title;

    @XmlElement(namespace = XmlRecord.NAMESPACE, name = "description", required = false)
    private String description;

    RecordSet build() {
        throw new UnsupportedOperationException();
    }

}
