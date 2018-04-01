package net.ramify.model;

import javax.annotation.CheckForNull;

public interface Provider<K, V> {

    @CheckForNull
    V get(K key);

}
