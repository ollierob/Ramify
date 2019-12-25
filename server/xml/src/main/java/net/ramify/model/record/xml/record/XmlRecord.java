package net.ramify.model.record.xml.record;

import net.ramify.model.record.RecordId;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.UUID;

import static net.ramify.utils.StringUtils.isBlank;

@XmlSeeAlso({XmlResidenceRecord.class})
public abstract class XmlRecord {

    public static final String NAMESPACE = "http://ramify.net/records";

    @XmlAttribute(name = "id")
    private String recordId;

    protected RecordId recordId() {
        return new RecordId(isBlank(recordId) ? UUID.randomUUID().toString() : recordId);
    }

    public abstract int numIndividuals();

}
