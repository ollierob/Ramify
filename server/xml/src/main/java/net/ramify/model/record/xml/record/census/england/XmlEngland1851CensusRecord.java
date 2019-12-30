package net.ramify.model.record.xml.record.census.england;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import net.ramify.model.place.Place;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.uk.Census1851Record;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.country.uk.XmlUkRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(namespace = XmlUkRecord.NAMESPACE, name = "census1851")
public class XmlEngland1851CensusRecord extends XmlEnglandCensusRecord {

    @XmlElement(namespace = XmlUkRecord.NAMESPACE, name = "entry")
    private List<XmlEngland1851CensusIndividual> individuals;

    @Override
    public int numIndividuals() {
        return individuals.size();
    }

    @Override
    protected Census1851Record build(final RecordContext context, final Place censusPlace, final RecordSet recordSet) {
        final var place = this.place(context, censusPlace);
        final var head = this.head(context);
        final var others = this.others(context);
        return new Census1851Record(this.recordId(), recordSet, place, head, others, context.uniqueEventMerger());
    }

    @Nonnull
    Census1851Record.Census1851Head head(final RecordContext context) {
        return Iterables.find(individuals, XmlEnglandCensusIndividual::isHead).toHead(context);
    }

    List<Census1851Record.Census1851Resident> others(final RecordContext context) {
        if (individuals == null || individuals.size() <= 1) return Collections.emptyList();
        return individuals.stream().filter(XmlEnglandCensusIndividual::isNotHead).map(i -> i.toOther(context)).collect(Collectors.toList());
    }

    @XmlType(namespace = XmlUkRecord.NAMESPACE)
    public static class XmlEngland1851CensusIndividual extends XmlEnglandCensusIndividual {

        Census1851Record.Census1851Head toHead(final RecordContext context) {
            Preconditions.checkState(this.isHead());
            return new Census1851Record.Census1851Head(
                    this.personId(),
                    this.name(context.nameParser()),
                    this.sex(),
                    this.marriageCondition(),
                    this.age(),
                    this.placeOfBirth(context.places()),
                    this.occupation(),
                    this.disability());
        }

        Census1851Record.Census1851Resident toOther(final RecordContext context) {
            Preconditions.checkState(!this.isHead());
            return new Census1851Record.Census1851Resident(
                    this.personId(),
                    this.name(context.nameParser()),
                    this.sex(),
                    this.relationshipToHead(),
                    this.marriageCondition(),
                    this.age(),
                    this.placeOfBirth(context.places()),
                    this.occupation(),
                    this.disability());
        }

    }

}
