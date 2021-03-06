package net.ramify.model.record.xml.record.census.england;

import com.google.common.base.MoreObjects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.meerkat.functions.consumers.Consumers;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.place.Place;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.uk.Census1821Record;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.country.uk.XmlUkRecord;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Period;

@XmlRootElement(namespace = XmlUkRecord.NAMESPACE, name = "census1821ByAge")
public class XmlEngland1821ByAgeCensusRecord extends XmlEnglandCensusRecord {

    static final Age UNDER_FIVE = Age.betweenInclusive(Period.ZERO, Period.of(5, 0, -1));
    static final Age FIVE_TO_TEN = Age.betweenInclusive(Period.ofYears(5), Period.of(10, 0, -1));
    static final Age TEN_TO_FIFTEEN = Age.betweenInclusive(Period.ofYears(10), Period.of(15, 0, -1));
    static final Age FIFTEEN_TO_TWENTY = Age.betweenInclusive(Period.ofYears(15), Period.of(20, 0, -1));
    static final Age TWENTY_TO_THIRTY = Age.betweenInclusive(Period.ofYears(20), Period.of(30, 0, -1));
    static final Age THIRTY_TO_FORTY = Age.betweenInclusive(Period.ofYears(30), Period.of(40, 0, -1));
    static final Age FORTY_TO_FIFTY = Age.betweenInclusive(Period.ofYears(40), Period.of(50, 0, -1));
    static final Age FIFTY_TO_SIXTY = Age.betweenInclusive(Period.ofYears(50), Period.of(60, 0, -1));
    static final Age SIXTY_TO_SEVENTY = Age.betweenInclusive(Period.ofYears(60), Period.of(70, 0, -1));

    @XmlElement(name = "head", required = true, namespace = XmlRecord.NAMESPACE)
    private XmlResidenceRecord head;

    @XmlAttribute(name = "malesUnderFive")
    private Integer malesUnderFive;

    @XmlAttribute(name = "malesFiveToTen")
    private Integer malesFiveToTen;

    @XmlAttribute(name = "malesTenToFifteen")
    private Integer malesTenToFifteen;

    @XmlAttribute(name = "malesThirtyToForty")
    private Integer malesThirtyToForty;

    @XmlAttribute(name = "malesFortyToFifty")
    private Integer malesFortyToFifty;

    @XmlAttribute(name = "malesFiftyToSixty")
    private Integer malesFiftyToSixty;

    @XmlAttribute(name = "malesSixtyToSeventy")
    private Integer malesSixtyToSeventy;

    @XmlAttribute(name = "femalesUnderFive")
    private Integer femalesUnderFive;

    @XmlAttribute(name = "femalesFiveToTen")
    private Integer femalesFiveToTen;

    @Override
    public int numIndividuals() {
        return this.ageCounts().values().stream().mapToInt(i -> i).sum();
    }

    @Override
    protected Census1821Record build(final RecordContext context, final Place censusPlace, final RecordSet recordSet) {
        final var id = this.recordId();
        final var head = this.head.person(censusPlace, context.onDate(Census1821Record.CENSUS_DATE));
        final var place = MoreObjects.firstNonNull(this.place(context.places()), censusPlace);
        final var ageCounts = this.ageCounts();
        return new Census1821Record(
                id,
                recordSet,
                place,
                head,
                ageCounts,
                context.uniqueEventMerger());
    }

    @Nonnull
    private Table<Gender, Age, Integer> ageCounts() {
        final var table = HashBasedTable.<Gender, Age, Integer>create();
        Consumers.ifNonNull(malesUnderFive, count -> table.put(Gender.MALE, UNDER_FIVE, count));
        Consumers.ifNonNull(malesFiveToTen, count -> table.put(Gender.MALE, FIVE_TO_TEN, count));
        Consumers.ifNonNull(malesTenToFifteen, count -> table.put(Gender.MALE, TEN_TO_FIFTEEN, count));
        Consumers.ifNonNull(malesThirtyToForty, count -> table.put(Gender.MALE, THIRTY_TO_FORTY, count));
        Consumers.ifNonNull(malesFortyToFifty, count -> table.put(Gender.MALE, FORTY_TO_FIFTY, count));
        Consumers.ifNonNull(malesFiftyToSixty, count -> table.put(Gender.MALE, FIFTY_TO_SIXTY, count));
        Consumers.ifNonNull(malesSixtyToSeventy, count -> table.put(Gender.MALE, SIXTY_TO_SEVENTY, count));
        Consumers.ifNonNull(femalesUnderFive, count -> table.put(Gender.FEMALE, UNDER_FIVE, count));
        return table;
    }

}
