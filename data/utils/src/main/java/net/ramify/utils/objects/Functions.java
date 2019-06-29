package net.ramify.utils.objects;

public class Functions {

    public static <F, T, X extends Exception> T ifNonNull(final F element, final ThrowingFunction<? super F, ? extends T, X> transform) throws X {
        return element != null ? transform.apply(element) : null;
    }

}
