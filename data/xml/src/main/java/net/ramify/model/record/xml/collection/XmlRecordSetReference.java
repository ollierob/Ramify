package net.ramify.model.record.xml.collection;

import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "recordSetReference")
class XmlRecordSetReference {

    @XmlAttribute(name = "reference", required = true)
    private String reference;

    @XmlAttribute(name = "archive", required = true)
    private String archive;

    @Nonnull
    RecordSetReference build() {
        return new DefaultRecordSetReference(
                reference,
                archive,
                null //TODO
        );
    }

}