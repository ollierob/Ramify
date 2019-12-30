package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.event.infer.MarriageConditionEventInference;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.xml.record.XmlPersonOnDateRecord;
import net.ramify.model.record.xml.record.country.uk.XmlUkRecord;
import net.ramify.model.relationship.RelationshipFactory;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlUkRecord.NAMESPACE)
public abstract class XmlEnglandCensusIndividual extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "relationshipToHead", required = true)
    private CensusRelationshipToHead relationshipToHead;

    @XmlAttribute(name = "marriageCondition")
    private CensusMarriageCondition marriageCondition;

    @XmlAttribute(name = "placeOfBirth")
    private String placeOfBirth;

    @XmlAttribute(name = "disability")
    private String disability;

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

    @CheckForNull
    protected String disability() {
        return disability;
    }

    @Nonnull
    protected MarriageConditionEventInference marriageCondition() {
        return marriageCondition == null ? MarriageConditionEventInference.NONE : marriageCondition.inference();
    }

}
