package net.ramify.utils.objects;

public interface ThrowingFunction<F, T, X extends Exception> {

    T apply(F from) throws X;

}
