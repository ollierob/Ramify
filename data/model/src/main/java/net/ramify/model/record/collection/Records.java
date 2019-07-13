package net.ramify.model.record.collection;

import com.google.common.base.Preconditions;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.Record;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Records extends BuildsProto<RecordProto.RecordList> {

    @Nonnull
    Stream<? extends Record> records();

    @Nonnull
    @CheckReturnValue
    default Records paginate(final int start, final int max) {
        Preconditions.checkArgument(start == 0);
        return () -> this.records().limit(max);
    }

    @Nonnull
    @Override
    default RecordProto.RecordList toProto() {
        return RecordProto.RecordList.newBuilder()
                .addAllRecord(this.records().map(Record::toProto).collect(Collectors.toList()))
                .build();
    }

}
