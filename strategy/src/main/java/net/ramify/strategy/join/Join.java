package net.ramify.strategy.join;

/**
 * @param <T>
 * @see net.ramify.strategy.merge.Merge
 */
public interface Join<T> {

    boolean join(T t1, T t2);

}
