package net.ramify.model.record.xml.record;

import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.burial.XmlBurialRecords;
import net.ramify.model.record.xml.record.mention.XmlMentionRecords;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecords;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collection;

@XmlSeeAlso({XmlMentionRecords.class, XmlResidenceRecords.class, XmlBurialRecords.class})
public abstract class XmlRecords {

    public abstract int size();

    @Nonnull
    public abstract Collection<? extends Record> build(
            RecordSet recordSet,
            RecordContext context);

}
