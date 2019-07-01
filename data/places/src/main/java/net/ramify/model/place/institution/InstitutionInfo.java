package net.ramify.model.place.institution;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.institution.church.ChurchInfo;
import net.ramify.model.place.proto.InstitutionProto;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

public interface InstitutionInfo extends HasPlace, BuildsProto<InstitutionProto.Institution> {

    @CheckForNull
    String description();

    @Nonnull
    DateRange founded();

    @CheckForNull
    DateRange closed();

    default boolean isClosed() {
        return this.closed() != null;
    }

    @Nonnull
    Set<? extends InstitutionRecordSetInfo> records();

    @Nonnull
    default InstitutionProto.Institution.Builder toProtoBuilder() {
        final var builder = InstitutionProto.Institution.newBuilder()
                .setPlace(this.place().toProto())
                .setDescription(MoreObjects.firstNonNull(this.description(), ""))
                .setEstablished(this.founded().toProto());
        Consumers.ifNonNull(this.closed(), c -> builder.setClosed(c.toProto()));
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
