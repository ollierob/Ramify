package net.ramify.model.place.id;

import net.ramify.model.place.PlaceId;

public class Spid extends PlaceId {

    public static Spid region(final String id) {
        return new Spid(Type.REGION, id);
    }

    public static Spid church(final String id) {
        return new Spid(Type.CHURCH, id);
    }

    private final Type type;

    private Spid(final Type type, final String value) {
        super(type.name().toLowerCase() + ':' + value);
        this.type = type;
    }

    private enum Type {

        REGION,
        CHURCH;

    }

}
