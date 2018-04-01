package net.ramify.model;

public interface Provider<K, V> {

    V get(K key);

}
