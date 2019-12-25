package net.ramify.model.record.xml.record;

import net.ramify.model.event.EventId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecord;

import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.UUID;

@XmlSeeAlso({XmlResidenceRecord.class})
public abstract class XmlRecord {

    public static final String NAMESPACE = "http://ramify.net/records";

    protected RecordId recordId() {
        return new RecordId(UUID.randomUUID().toString()); //FIXME
    }

    protected EventId randomEventId() {
        return EventId.random();
    }

    public abstract int numIndividuals();

}
