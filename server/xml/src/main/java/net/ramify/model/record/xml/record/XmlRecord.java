package net.ramify.model.record.xml.record;

import net.ramify.model.record.RecordId;
import net.ramify.model.record.image.ImageId;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecord;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.UUID;

import static net.ramify.utils.StringUtils.isBlank;

@XmlSeeAlso({XmlResidenceRecord.class})
public abstract class XmlRecord {

    public static final String NAMESPACE = "http://ramify.net/records";

    @XmlAttribute(name = "recordId")
    private String recordId;

    @XmlAttribute(name = "imageId")
    private String imageId;

    public abstract int numIndividuals();

    protected RecordId recordId() {
        return new RecordId(isBlank(recordId) ? UUID.randomUUID().toString() : recordId);
    }

    @CheckForNull
    protected ImageId imageId() {
        return isBlank(imageId) ? null : new ImageId(imageId);
    }

}
