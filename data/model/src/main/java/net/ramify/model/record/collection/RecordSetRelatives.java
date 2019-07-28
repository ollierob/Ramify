package net.ramify.model.record.collection;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public interface RecordSetRelatives extends BuildsProto<RecordProto.RecordSetRelatives> {

    @CheckForNull
    RecordSet parent();

    @Nonnull
    Set<RecordSet> next();

    @CheckForNull
    RecordSet previous();

    @Nonnull
    Set<RecordSet> children();

    @Nonnull
    default Stream<RecordSetId> childIds() {
        return this.children().stream().map(HasRecordSetId::recordSetId);
    }

    @Nonnull
    default RecordProto.RecordSetRelatives.Builder toProtoBuilder() {
        final var builder = RecordProto.RecordSetRelatives.newBuilder();
        Consumers.ifNonNull(this.parent(), parent -> builder.setParent(parent.toProto()));
        this.next().forEach(next -> builder.addNext(next.toProto()));
        Consumers.ifNonNull(this.previous(), parent -> builder.setPrevious(parent.toProto()));
        this.children().forEach(child -> builder.addChild(child.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default RecordProto.RecordSetRelatives toProto() {
        return this.toProtoBuilder().build();
    }

    RecordSetRelatives NONE = new RecordSetRelatives() {

        @CheckForNull
        @Override
        public RecordSet parent() {
            return null;
        }

        @CheckForNull
        @Override
        public Set<RecordSet> next() {
            return Collections.emptySet();
        }

        @CheckForNull
        @Override
        public RecordSet previous() {
            return null;
        }

        @Nonnull
        @Override
        public Set<RecordSet> children() {
            return Collections.emptySet();
        }
    };

}
