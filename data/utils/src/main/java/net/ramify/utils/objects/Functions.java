package net.ramify.utils.objects;

import java.util.function.Function;

public class Functions {

    public static <F, T> T ifNonNull(final F element, final Function<? super F, ? extends T> transform) {
        return element == null ? transform.apply(element) : null;
    }

}
