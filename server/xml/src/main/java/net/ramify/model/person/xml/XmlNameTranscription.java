package net.ramify.model.person.xml;

import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(namespace = XmlPerson.NAMESPACE, name = "nameTranscription")
public class XmlNameTranscription extends XmlName {

    @XmlAttribute(name = "original", required = true)
    private String originalName;

    @XmlAttribute(name = "assumed", required = true)
    private String assumedName;

    @Override
    public Name build(final NameParser nameParser) {
        return new TranscribedName(originalName, nameParser.parse(assumedName));
    }

    @XmlTransient
    static class TranscribedName implements Name {

        private final String original;
        private final Name assumed;

        TranscribedName(final String original, final Name assumed) {
            this.original = original;
            this.assumed = assumed;
        }

        @Nonnull
        @Override
        public String value() {
            return assumed.value();
        }

        @Override
        public boolean contains(final String name) {
            final var lc = name.toLowerCase();
            return assumed.contains(lc) || original.toLowerCase().contains(lc);
        }

        @Nonnull
        @Override
        public PersonProto.Name.Builder toProtoBuilder() {
            return assumed.toProtoBuilder().setOriginal(original);
        }
    }

}
