package net.ramify.model.record.xml.record.country.uk;

import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class XmlUkRecord extends XmlRecord {

    public static final String NAMESPACE = XmlRecord.NAMESPACE + "/gb";

}
