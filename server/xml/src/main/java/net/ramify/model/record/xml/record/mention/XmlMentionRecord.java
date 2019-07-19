package net.ramify.model.record.xml.record.mention;

import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mention")
public class XmlMentionRecord extends XmlRecord {

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    protected boolean deceased() {
        return Boolean.TRUE.equals(deceased);
    }

}
