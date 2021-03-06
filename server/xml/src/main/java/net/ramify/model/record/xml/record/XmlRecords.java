package net.ramify.model.record.xml.record;

import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.birth.XmlBaptismRecords;
import net.ramify.model.record.xml.record.birth.XmlBirthRecords;
import net.ramify.model.record.xml.record.census.XmlCensusRecords;
import net.ramify.model.record.xml.record.death.XmlBurialRecords;
import net.ramify.model.record.xml.record.death.XmlMemorialInscriptionRecords;
import net.ramify.model.record.xml.record.marriage.XmlMarriageRecords;
import net.ramify.model.record.xml.record.mention.XmlMentionRecords;
import net.ramify.model.record.xml.record.property.XmlPropertyTransactionRecords;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecords;
import net.ramify.model.record.xml.record.wills.XmlProbateRecords;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collection;

@XmlSeeAlso({
        XmlBirthRecords.class, XmlBaptismRecords.class,
        XmlMarriageRecords.class,
        XmlMentionRecords.class, XmlResidenceRecords.class, XmlPropertyTransactionRecords.class,
        XmlCensusRecords.class,
        XmlBurialRecords.class, XmlMemorialInscriptionRecords.class,
        XmlProbateRecords.class
})
public abstract class XmlRecords {

    public abstract int numRecords();

    public abstract int numIndividuals();

    @Nonnull
    public abstract Collection<? extends Record> build(
            RecordSet recordSet,
            RecordContext context);

}
