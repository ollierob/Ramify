package net.ramify.utils.objects;

import java.util.function.Consumer;

public class Consumers {

    public static <T> void ifNonNull(final T object, final Consumer<? super T> ifNonNull) {
        if (object != null) ifNonNull.accept(object);
    }

}
