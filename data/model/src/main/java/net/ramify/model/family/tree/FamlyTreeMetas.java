package net.ramify.model.family.tree;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.family.proto.FamilyProto;

import javax.annotation.Nonnull;
import java.util.Set;

public interface FamlyTreeMetas extends BuildsProto<FamilyProto.FamilyTreeList> {

    @Nonnull
    Set<FamilyTreeInfo> meta();

    @Nonnull
    @Override
    default FamilyProto.FamilyTreeList toProto() {
        return FamilyProto.FamilyTreeList.newBuilder()
                .addAllFamilyTree(Iterables.transform(this.meta(), FamilyTreeInfo::toProto))
                .build();
    }

}
