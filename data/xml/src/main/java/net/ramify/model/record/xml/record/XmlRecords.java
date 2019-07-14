package net.ramify.model.record.xml.record;

import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.record.burial.XmlBurialRecords;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecords;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collection;

@XmlSeeAlso({XmlResidenceRecords.class, XmlBurialRecords.class})
public abstract class XmlRecords {

    public abstract int size();

    @Nonnull
    public abstract Collection<? extends Record> build(
            RecordSet recordSet,
            NameParser nameParser,
            DateParser dateParser);

}
