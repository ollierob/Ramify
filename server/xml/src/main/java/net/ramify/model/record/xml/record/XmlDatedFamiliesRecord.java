package net.ramify.model.record.xml.record;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.family.Family;
import net.ramify.model.family.xml.XmlFamily;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.utils.collections.SetUtils;

import javax.xml.bind.annotation.XmlElementRef;
import java.util.List;
import java.util.Set;

public abstract class XmlDatedFamiliesRecord extends XmlDatedRecord {

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

}
