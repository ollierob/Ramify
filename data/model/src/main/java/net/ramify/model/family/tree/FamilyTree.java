package net.ramify.model.family.tree;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.family.Family;
import net.ramify.model.family.collection.HasFamilies;
import net.ramify.model.family.proto.FamilyProto;

import javax.annotation.Nonnull;

public interface FamilyTree extends HasFamilyTreeId, HasFamilies, BuildsProto<FamilyProto.FamilyTree> {

    @Nonnull
    FamilyTreeMeta meta();

    @Nonnull
    default String name() {
        return this.meta().name();
    }

    @Nonnull
    @Override
    default FamilyTreeId familyTreeId() {
        return this.meta().familyTreeId();
    }

    @Nonnull
    default FamilyProto.FamilyTree.Builder toProtoBuilder() {
        return this.meta()
                .toProtoBuilder()
                .addAllFamily(Iterables.transform(this.families(), Family::toProto));
    }

    @Nonnull
    @Override
    default FamilyProto.FamilyTree toProto() {
        return this.toProtoBuilder().build();
    }

}
