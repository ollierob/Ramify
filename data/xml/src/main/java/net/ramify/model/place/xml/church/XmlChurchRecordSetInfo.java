package net.ramify.model.place.xml.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.InYears;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.institution.church.record.ChurchAccountInfo;
import net.ramify.model.place.institution.church.record.ChurchBaptismInfo;
import net.ramify.model.place.institution.church.record.ChurchBirthInfo;
import net.ramify.model.place.institution.church.record.ChurchBurialInfo;
import net.ramify.model.place.institution.church.record.ChurchMarriageInfo;
import net.ramify.model.place.institution.church.record.ChurchMeetingMinutesInfo;
import net.ramify.model.place.institution.church.record.ChurchMembershipInfo;
import net.ramify.model.place.institution.church.record.ChurchPewRentalInfo;
import net.ramify.model.place.institution.church.record.ChurchRecordSetInfo;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSetId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "churchRecordSet")
public class XmlChurchRecordSetInfo implements HasRecordSetId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "type", required = true)
    private Type type;

    @XmlAttribute(name = "startYear")
    private int startYear;

    @XmlAttribute(name = "endYear")
    private int endYear;

    @XmlValue
    private String notes;

    public ChurchRecordSetInfo build(final DateParser parser) {
        return type.build(this, parser);
    }

    @Nonnull
    DateRange date() {
        return new InYears(startYear, endYear);
    }

    @CheckForNull
    String notes() {
        return notes;
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return new RecordSetId(id);
    }

    @SuppressWarnings("ValidExternallyBoundObject")
    @XmlType(namespace = XmlPlace.NAMESPACE)
    enum Type {

        @XmlEnumValue("baptism")
        BAPTISM {
            @Override
            ChurchRecordSetInfo build(final XmlChurchRecordSetInfo info, final DateParser dateParser) {
                return new ChurchBaptismInfo(info.recordSetId(), info.date(), info.notes());
            }
        },

        @XmlEnumValue("birth")
        BIRTH {
            @Override
            ChurchRecordSetInfo build(final XmlChurchRecordSetInfo info, final DateParser dateParser) {
                return new ChurchBirthInfo(info.recordSetId(), info.date(), info.notes());
            }
        },

        @XmlEnumValue("marriage")
        MARRIAGE {
            @Override
            ChurchRecordSetInfo build(final XmlChurchRecordSetInfo info, final DateParser dateParser) {
                return new ChurchMarriageInfo(info.recordSetId(), info.date(), info.notes);
            }
        },

        @XmlEnumValue("burial")
        BURIAL {
            @Override
            ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser) {
                return new ChurchBurialInfo(info.recordSetId(), info.date(), info.notes);
            }
        },

        @XmlEnumValue("membership")
        MEMBERSHIP {
            @Override
            ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser) {
                return new ChurchMembershipInfo(info.recordSetId(), info.date(), info.notes);
            }
        },
        @XmlEnumValue("pewRents")
        PEW_RENTS {
            @Override
            ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser) {
                return new ChurchPewRentalInfo(info.recordSetId(), info.date(), info.notes);
            }
        },
        @XmlEnumValue("accounts")
        ACCOUNTS {
            @Override
            ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser) {
                return new ChurchAccountInfo(info.recordSetId(), info.date(), info.notes);
            }
        },
        @XmlEnumValue("minutes")
        MINUTES {
            @Override
            ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser) {
                return new ChurchMeetingMinutesInfo(info.recordSetId(), info.date(), info.notes);
            }
        };

        abstract ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser);

    }

}
