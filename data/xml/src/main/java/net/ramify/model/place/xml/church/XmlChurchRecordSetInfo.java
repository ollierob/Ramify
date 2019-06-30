package net.ramify.model.place.xml.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.InYears;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.church.record.ChurchBaptismInfo;
import net.ramify.model.place.church.record.ChurchRecordSetInfo;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.record.set.HasRecordSetId;
import net.ramify.model.record.set.RecordSetId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

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

        @XmlEnumValue("marriage")
        MARRIAGE {
            @Override
            ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser) {
                throw new UnsupportedOperationException(); //TODO
            }
        },

        @XmlEnumValue("burial")
        BURIAL {
            @Override
            ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser) {
                throw new UnsupportedOperationException(); //TODO
            }
        },

        @XmlEnumValue("membership")
        MEMBERSHIP {
            @Override
            ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser) {
                throw new UnsupportedOperationException(); //TODO
            }
        };

        abstract ChurchRecordSetInfo build(XmlChurchRecordSetInfo info, DateParser dateParser);

    }

}
