package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.event.infer.MarriageConditionEventInference;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.xml.record.XmlPersonOnDateRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.relationship.RelationshipFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlRecord.NAMESPACE)
public abstract class XmlEnglandCensusIndividual extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "relationshipToHead", required = true)
    private CensusRelationshipToHead relationshipToHead;

    @XmlAttribute(name = "marriageCondition")
    private CensusMarriageCondition marriageCondition;

    @XmlAttribute(name = "occupation")
    private String occupation;

    @XmlAttribute(name = "placeOfBirth")
    private String placeOfBirth;

    public boolean isHead() {
        return relationshipToHead.isHead();
    }

    public boolean isNotHead() {
        return !this.isHead();
    }

    protected Place placeOfBirth(final PlaceProvider places) {
        return places.require(new PlaceId(placeOfBirth));
    }

    protected RelationshipFactory relationshipToHead() {
        return relationshipToHead;
    }

    protected MarriageConditionEventInference marriageCondition() {
        return marriageCondition == null ? MarriageConditionEventInference.NONE : marriageCondition.inference();
    }

    protected String notes() {
        return occupation == null ? super.notes() : occupation;
    }

}
