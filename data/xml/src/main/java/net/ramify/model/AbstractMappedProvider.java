package net.ramify.model;

import com.google.common.collect.ImmutableMap;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Map;

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

    protected void putAll(final Map<? extends K, ? extends V> map) {
        this.map.putAll(map);
    }

    public int size() {
        return map.size();
    }

    public ImmutableMap<K, V> immutableMap() {
        return ImmutableMap.copyOf(map);
    }

}
