package net.ramify.model.record.xml.collection;

import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "recordSet")
class XmlRecordSet {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "title", required = true)
    private String title;

    @XmlAttribute(name = "place", required = true)
    private String place;

    @XmlElement(namespace = XmlRecord.NAMESPACE, name = "description", required = false)
    private String description;

    @XmlElementRef
    private List<XmlRecordSet> children;

    Collection<RecordSet> build() {
        throw new UnsupportedOperationException();
    }

    Collection<RecordSet> build(final RecordSetId parentId) {
        throw new UnsupportedOperationException();
    }

}
