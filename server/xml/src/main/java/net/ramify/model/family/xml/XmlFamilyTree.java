package net.ramify.model.family.xml;

import com.google.common.base.Preconditions;
import net.ramify.model.family.Family;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.FamilyTreeInfo;
import net.ramify.model.record.xml.RecordContext;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@XmlType(namespace = XmlFamily.NAMESPACE, name = "familyTree")
@XmlRootElement(name = "familyTree")
public class XmlFamilyTree {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElementRef
    private List<XmlFamily> families;

    @Nonnull
    public FamilyTree toFamilyTree(final RecordContext context) {
        Preconditions.checkState(families != null && !families.isEmpty(), "No families defined");
        final Set<Family> families = this.families.stream()
                .map(family -> family.toFamily(context))
                .collect(Collectors.toSet());
        return new DefaultFamilyTree(name, families);
    }

    @XmlTransient
    private static class DefaultFamilyTree implements FamilyTree {

        private final String name;
        private final Set<Family> families;

        private DefaultFamilyTree(final String name, final Set<Family> families) {
            this.name = name;
            this.families = families;
        }

        @Nonnull
        @Override
        public FamilyTreeInfo info() {
            return new DefaultInfo(name, this.numPeople());
        }

        @Nonnull
        @Override
        public Set<? extends Family> families() {
            return families;
        }

    }

    @XmlTransient
    private static class DefaultInfo implements FamilyTreeInfo {

        private final String name;
        private final int numPeople;

        private DefaultInfo(final String name, final int numPeople) {
            this.name = name;
            this.numPeople = numPeople;
        }

        @Nonnull
        @Override
        public String name() {
            return name;
        }

        @Override
        public int numPeople() {
            return numPeople;
        }

        @Nonnull
        @Override
        public FamilyTreeId familyTreeId() {
            return new FamilyTreeId(name);
        }

    }

}
