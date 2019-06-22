package net.ramify.model;

public interface SelfTyped<T extends SelfTyped<? super T>> {

    @SuppressWarnings("unchecked")
    default T self() {
        return (T) this;
    }

}
