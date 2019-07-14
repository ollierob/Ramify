package net.ramify.model;

import com.google.common.collect.ImmutableMap;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@XmlTransient
public abstract class AbstractMappedProvider<K, V> implements Provider<K, V> {

    private final Map<K, V> map;

    protected AbstractMappedProvider(final Map<K, V> map) {
        this.map = map;
    }

    @CheckForNull
    @Override
    public V get(K key) {
        return map.get(key);
    }

    protected Set<K> keys() {
        return map.keySet();
    }

    protected void putAll(final Map<? extends K, ? extends V> map) {
        this.map.putAll(map);
    }

    public int size() {
        return map.size();
    }

    protected Map<K, V> map() {
        return Collections.unmodifiableMap(map);
    }

    protected ImmutableMap<K, V> immutableMap() {
        return ImmutableMap.copyOf(map);
    }

}