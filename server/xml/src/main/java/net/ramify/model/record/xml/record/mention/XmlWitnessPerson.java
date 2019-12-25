package net.ramify.model.record.xml.record.mention;

import net.ramify.model.record.xml.record.XmlPersonRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "witness")
public class XmlWitnessPerson extends XmlPersonRecord {

}
