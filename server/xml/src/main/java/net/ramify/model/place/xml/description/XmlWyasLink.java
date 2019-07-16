package net.ramify.model.place.xml.description;

import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.util.proto.LinkProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "wyasLink")
class XmlWyasLink extends XmlLink {

    @CheckForNull
    @Override
    public String text() {
        return "West Yorkshire Archive Services";
    }

    @Nonnull
    @Override
    public LinkProto.Link.Builder toProtoBuilder() {
        return super.toProtoBuilder()
                .setType(LinkProto.Link.Type.WYAS);
    }

}
