package net.ramify.model.record.xml.record.mention;

import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.LifeEventRecord;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
interface XmlMentionRecord {

    int numIndividuals();

    LifeEventRecord buildRecord(RecordContext context, RecordSet recordSet);

}
