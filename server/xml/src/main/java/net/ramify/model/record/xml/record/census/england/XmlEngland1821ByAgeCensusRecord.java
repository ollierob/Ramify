package net.ramify.model.record.xml.record.census.england;

import com.google.common.base.MoreObjects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.place.Place;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.uk.Census1821Record;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.census.XmlCensusRecord;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecord;
import net.ramify.utils.objects.Consumers;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.time.Period;

@XmlType(namespace = XmlRecord.NAMESPACE, name = "censusEngland1821ByAge")
public class XmlEngland1821ByAgeCensusRecord extends XmlCensusRecord {

    static final Age UNDER_FIVE = Age.between(Period.ZERO, Period.of(5, 0, -1));
    static final Age FIVE_TO_TEN = Age.between(Period.ofYears(5), Period.of(10, 0, -1));

    @XmlElement(name = "head", required = true, namespace = XmlRecord.NAMESPACE)
    private XmlResidenceRecord head;

    @XmlAttribute(name = "malesUnderFive")
    private Integer malesUnderFive;

    @XmlAttribute(name = "malesFiveToTen")
    private Integer malesFiveToTen;

    @XmlAttribute(name = "femalesUnderFive")
    private Integer femalesUnderFive;

    @Override
    protected int size() {
        return 1;
    }

    @Override
    protected Census1821Record build(final RecordContext context, final Place censusPlace, final RecordSet recordSet) {
        final var id = this.recordId();
        final var head = this.head.person(censusPlace, Census1821Record.CENSUS_DATE, context);
        final var place = MoreObjects.firstNonNull(this.place(context.places()), censusPlace);
        final var ageCounts = this.ageCounts();
        return new Census1821Record(
                id,
                recordSet.recordSetId(),
                place,
                head,
                ageCounts);
    }

    private Table<Gender, Age, Integer> ageCounts() {
        final var table = HashBasedTable.<Gender, Age, Integer>create();
        Consumers.ifNonNull(malesUnderFive, count -> table.put(Gender.MALE, UNDER_FIVE, count));
        Consumers.ifNonNull(malesFiveToTen, count -> table.put(Gender.MALE, FIVE_TO_TEN, count));
        Consumers.ifNonNull(femalesUnderFive, count -> table.put(Gender.FEMALE, UNDER_FIVE, count));
        return table;
    }

}
