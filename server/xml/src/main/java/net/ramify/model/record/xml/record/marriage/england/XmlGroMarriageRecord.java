package net.ramify.model.record.xml.record.marriage.england;

import net.ramify.model.place.PlaceId;
import net.ramify.model.record.civil.uk.GeneralRegisterMarriage;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.marriage.XmlMarriageRecord;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "groMarriage")
public class XmlGroMarriageRecord extends XmlMarriageRecord {

    @Override
    public int numIndividuals() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public GeneralRegisterMarriage build(final RecordContext context, final RecordSet recordSet, final PlaceId marriagePlace) {
        throw new UnsupportedOperationException(); //TODO
    }

}
