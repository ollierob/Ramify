package net.ramify.utils.objects;

public class Functions {

    public static <F, T, X extends Exception> T ifNonNull(final F element, final ThrowingFunction<? super F, ? extends T, X> transform) throws X {
        return ifNonNull(element, transform, null);
    }

    public static <F, T, X extends Exception> T ifNonNull(final F element, final ThrowingFunction<? super F, ? extends T, X> transform, final T defaultValue) throws X {
        final T transformed = element == null ? null : transform.apply(element);
        return transformed != null ? transformed : defaultValue;
    }

}
