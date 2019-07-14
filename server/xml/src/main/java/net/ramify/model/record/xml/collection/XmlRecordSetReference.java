package net.ramify.model.record.xml.collection;

import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.util.Link;
import net.ramify.utils.objects.Functions;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "recordSetReference")
class XmlRecordSetReference {

    @XmlAttribute(name = "reference", required = true)
    private String reference;

    @XmlAttribute(name = "archive", required = true)
    private String archive;

    @XmlAttribute(name = "link")
    private String link;

    @Nonnull
    RecordSetReference build() {
        return new DefaultRecordSetReference(
                reference,
                archive,
                Functions.ifNonNull(link, link -> Link.of(link, null)));
    }

}
