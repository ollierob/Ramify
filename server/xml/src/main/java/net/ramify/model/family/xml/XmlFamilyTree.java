package net.ramify.model.family.xml;

import com.google.common.base.Preconditions;
import net.ramify.model.ParserContext;
import net.ramify.model.family.Family;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.FamilyTreeMeta;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@XmlType(namespace = XmlFamily.NAMESPACE, name = "familyTree")
@XmlRootElement(name = "familyTree")
public class XmlFamilyTree {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElementRef
    private List<XmlFamily> families;

    @Nonnull
    public FamilyTree toFamilyTree(final ParserContext context) {
        Preconditions.checkState(families != null && !families.isEmpty(), "No families defined");
        final AtomicInteger numPeople = new AtomicInteger();
        final Set<Family> families = this.families.stream()
                .map(family -> family.toFamily(context))
                .peek(family -> numPeople.addAndGet(family.numPeople()))
                .collect(Collectors.toSet());
        final var meta = new NamedMeta(name, numPeople.get());
        return new DefaultFamilyTree(meta, families);
    }

    @XmlTransient
    private static class DefaultFamilyTree implements FamilyTree {

        private final FamilyTreeMeta meta;
        private final Set<Family> families;

        private DefaultFamilyTree(FamilyTreeMeta meta, Set<Family> families) {
            this.meta = meta;
            this.families = families;
        }

        @Nonnull
        @Override
        public FamilyTreeMeta meta() {
            return meta;
        }

        @Nonnull
        @Override
        public Set<? extends Family> families() {
            return families;
        }

    }

    @XmlTransient
    private static class NamedMeta implements FamilyTreeMeta {

        private final String name;
        private final int numPeople;

        private NamedMeta(String name, int numPeople) {
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
