package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.event.infer.MarriageConditionEventInference;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(namespace = XmlRecord.NAMESPACE)
public enum CensusMarriageCondition {

    @XmlEnumValue("married")
    MARRIED(MarriageConditionEventInference.MARRIED),

    @XmlEnumValue("unmarried")
    UNMARRIED(MarriageConditionEventInference.UNMARRIED),

    @XmlEnumValue("widowed")
    WIDOWED(MarriageConditionEventInference.WIDOWED),;

    private final MarriageConditionEventInference inference;

    CensusMarriageCondition(MarriageConditionEventInference inference) {
        this.inference = inference;
    }

    public MarriageConditionEventInference inference() {
        return inference;
    }

}
