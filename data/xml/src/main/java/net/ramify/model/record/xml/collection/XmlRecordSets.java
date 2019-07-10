package net.ramify.model.record.xml.collection;

import net.ramify.model.record.collection.RecordSet;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.ramify.model.record.xml.record.XmlRecord.NAMESPACE;

@XmlType(namespace = NAMESPACE, name = "records")
@XmlRootElement(name = "records")
public class XmlRecordSets {

    @XmlElementRef
    private List<XmlRecordSet> recordSets;

    @Nonnull
    public Collection<RecordSet> recordSets() {
        if (recordSets == null) return Collections.emptySet();
        return SetUtils.transform(recordSets, XmlRecordSet::build);
    }

}
