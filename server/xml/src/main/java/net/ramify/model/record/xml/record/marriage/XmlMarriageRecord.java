package net.ramify.model.record.xml.record.marriage;

import net.ramify.model.place.Place;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.MarriageRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlRecord.NAMESPACE)
public abstract class XmlMarriageRecord extends XmlRecord {

    public abstract int numIndividuals();

    public abstract MarriageRecord build(RecordContext context, RecordSet recordSet, Place marriagePlace);

}
