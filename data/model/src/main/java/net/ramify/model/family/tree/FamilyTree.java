package net.ramify.model.family.tree;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.family.Family;
import net.ramify.model.family.collection.HasFamilies;
import net.ramify.model.family.proto.FamilyProto;

import javax.annotation.Nonnull;

public interface FamilyTree extends HasFamilyTreeId, HasFamilies, BuildsProto<FamilyProto.FamilyTree> {

    @Nonnull
    String name();

    @Nonnull
    default FamilyProto.FamilyTree.Builder toProtoBuilder() {
        return FamilyProto.FamilyTree.newBuilder()
                .setId(this.familyTreeId().value())
                .setName(this.name())
                .addAllFamily(Iterables.transform(this.families(), Family::toProto));
    }

    @Nonnull
    @Override
    default FamilyProto.FamilyTree toProto() {
        return this.toProtoBuilder().build();
    }

}
