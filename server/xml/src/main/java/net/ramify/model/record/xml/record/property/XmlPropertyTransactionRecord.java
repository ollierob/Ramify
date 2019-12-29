package net.ramify.model.record.xml.record.property;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.family.Family;
import net.ramify.model.family.xml.XmlFamily;
import net.ramify.model.record.Record;
import net.ramify.model.record.civil.GenericPropertyTransactionRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlDatedRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "propertyRecord")
public class XmlPropertyTransactionRecord extends XmlDatedRecord {

    @XmlElementRef(namespace = XmlFamily.NAMESPACE)
    private List<XmlFamily> families;

    @Override
    public int numIndividuals() {
        if (families == null) return 0;
        return families.stream().mapToInt(XmlFamily::numPeople).sum();
    }

    protected Set<Family> families(final RecordContext context) {
        if (families == null) return ImmutableSet.of();
        return SetUtils.transform(families, family -> family.toFamily(context));
    }

    @Nonnull
    public Record toRecord(final RecordSet recordSet, final RecordContext context) {
        return new GenericPropertyTransactionRecord(
                this.recordId(),
                recordSet,
                this.date(context),
                this.families(context));
    }

}
