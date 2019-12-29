package net.ramify.model.record.xml.record.property;

import net.ramify.model.record.Record;
import net.ramify.model.record.civil.GenericPropertyTransactionRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlDatedFamiliesRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "propertyRecord")
public class XmlPropertyTransactionRecord extends XmlDatedFamiliesRecord {

    @Nonnull
    public Record toRecord(final RecordSet recordSet, final RecordContext context) {
        return new GenericPropertyTransactionRecord(
                this.recordId(),
                recordSet,
                this.date(context),
                this.families(context));
    }

}
