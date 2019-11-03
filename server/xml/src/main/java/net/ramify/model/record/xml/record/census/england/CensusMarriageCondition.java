package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.event.Event;
import net.ramify.model.event.infer.MarriageConditionEventInference;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.Record;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.Set;

@XmlEnum
@XmlType(namespace = XmlRecord.NAMESPACE)
public enum CensusMarriageCondition implements MarriageConditionEventInference {

    @XmlEnumValue("married")
    MARRIED(MarriageConditionEventInference.MARRIED),

    @XmlEnumValue("unmarried")
    UNMARRIED(MarriageConditionEventInference.UNMARRIED),

    @XmlEnumValue("widowed")
    WIDOWED(MarriageConditionEventInference.WIDOWED);

    private final MarriageConditionEventInference inference;

    CensusMarriageCondition(MarriageConditionEventInference inference) {
        this.inference = inference;
    }

    @Nonnull
    @Override
    public Set<Event> inferEvents(PersonId personId, Record record) {
        return inference.inferEvents(personId, record);
    }
}
