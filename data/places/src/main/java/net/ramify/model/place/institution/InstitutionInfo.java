package net.ramify.model.place.institution;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.institution.church.ChurchInfo;
import net.ramify.model.place.proto.InstitutionProto;
import net.ramify.model.record.collection.RecordSet;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

public interface InstitutionInfo extends HasPlace, BuildsProto<InstitutionProto.Institution> {

    @CheckForNull
    String type();

    @Nonnull
    Set<RecordSet> records();

    @Nonnull
    default InstitutionProto.Institution.Builder toProtoBuilder() {
        final var builder = InstitutionProto.Institution.newBuilder()
                .setPlace(this.place().toProto())
                .setType(MoreObjects.firstNonNull(this.type(), ""));
        this.records().forEach(record -> builder.addRecordSet(record.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default InstitutionProto.Institution toProto() {
        return this.toProtoBuilder().build();
    }

    @Nonnull
    static InstitutionProto.InstitutionList toProto(final Collection<ChurchInfo> churches) {
        final var list = InstitutionProto.InstitutionList.newBuilder();
        list.addAllInstitution(Iterables.transform(churches, ChurchInfo::toProto));
        return list.build();
    }

}
