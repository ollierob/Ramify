package net.ramify.model.record.xml.record.mention;

import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.GenericMentionFamiliesRecord;
import net.ramify.model.record.type.LifeEventRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlDatedFamiliesRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mentionPeople")
public class XmlMentionPeopleRecord extends XmlDatedFamiliesRecord implements XmlMentionRecord {

    @Override
    public LifeEventRecord buildRecord(final RecordContext context, final RecordSet recordSet) {
        return new GenericMentionFamiliesRecord(
                this.recordId(),
                recordSet,
                this.families(context),
                context.recordDate());
    }

}
