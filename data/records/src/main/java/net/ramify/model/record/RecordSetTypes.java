package net.ramify.model.record;

import com.google.common.collect.Maps;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.record.collection.RecordSetType;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Map;

public class RecordSetTypes {

    private static final Map<String, RecordSetType> typesByName = Maps.newConcurrentMap();

    public static final RecordSetType BAPTISM = type("Baptism", EventProto.RecordType.BAPTISM);
    public static final RecordSetType BIRTH = type("Birth", EventProto.RecordType.BIRTH);
    public static final RecordSetType BURIAL = type("Burial", EventProto.RecordType.BURIAL);
    public static final RecordSetType CENSUS = type("Census", EventProto.RecordType.CENSUS);
    public static final RecordSetType DEATH = type("Death", EventProto.RecordType.DEATH);
    public static final RecordSetType DIVORCE = type("Divorce", EventProto.RecordType.DIVORCE);
    public static final RecordSetType MARRIAGE = type("Marriage", EventProto.RecordType.MARRIAGE);
    public static final RecordSetType MEMBERSHIP = type("Membership", EventProto.RecordType.MEMBERSHIP);
    public static final RecordSetType MEMORIAL = type("Memorial", EventProto.RecordType.MEMORIAL);
    public static final RecordSetType MENTION = type("Mention", EventProto.RecordType.MENTION);
    public static final RecordSetType PAYMENT = type("Payment", EventProto.RecordType.PAYMENT);
    public static final RecordSetType PROBATE = type("Probate", EventProto.RecordType.PROBATE);
    public static final RecordSetType RESIDENCE = type("Residence", EventProto.RecordType.RESIDENCE);
    public static final RecordSetType TRANSACTION = type("Transaction", EventProto.RecordType.TRANSACTION);
    public static final RecordSetType WILL = type("Will", EventProto.RecordType.WILL);

    public static final RecordSetType MIXED = type("Mixed", EventProto.RecordType.MIXED);
    public static final RecordSetType UNSPECIFIED = type("Unspecified", EventProto.RecordType.UNSPECIFIED);

    private static RecordSetType type(final String name, final EventProto.RecordType protoType) {
        final var type = new RecordSetType() {

            @Nonnull
            @Override
            public String name() {
                return name;
            }

            @Nonnull
            @Override
            public EventProto.RecordType toProto() {
                return protoType;
            }

        };
        typesByName.put(protoType.name().toLowerCase(), type);
        typesByName.put(type.name().toLowerCase(), type);
        return type;
    }

    @CheckForNull
    public static RecordSetType valueOf(final String value) {
        return typesByName.get(value.toLowerCase());
    }

}
