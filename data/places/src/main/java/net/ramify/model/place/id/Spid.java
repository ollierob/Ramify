package net.ramify.model.place.id;

import net.ramify.model.place.PlaceId;

public class Spid extends PlaceId {

    public static Spid church(final String id) {
        return new Spid(Type.CHURCH, id);
    }

    private Spid(final Type type, final String value) {
        super(type.name().toLowerCase() + ':' + value);
    }

    private enum Type {

        CHURCH;

    }

}
