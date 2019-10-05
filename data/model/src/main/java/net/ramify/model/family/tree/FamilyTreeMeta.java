package net.ramify.model.family.tree;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.family.proto.FamilyProto;

import javax.annotation.Nonnull;

public interface FamilyTreeMeta extends HasFamilyTreeId, BuildsProto<FamilyProto.FamilyTree> {

    @Nonnull
    String name();

    int numPeople();

    @Nonnull
    default FamilyProto.FamilyTree.Builder toProtoBuilder() {
        return FamilyProto.FamilyTree.newBuilder()
                .setId(this.familyTreeId().value())
                .setName(this.name())
                .setNumPeople(this.numPeople());
    }

    @Nonnull
    @Override
    default FamilyProto.FamilyTree toProto() {
        return this.toProtoBuilder().build();
    }

}
