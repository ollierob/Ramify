package net.ramify.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.ramify.model.util.provider.Provider;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
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

    protected void put(final K key, final V value) {
        this.map.put(key, value);
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

    protected Map<K, V> mutableMap() {
        return Maps.newHashMap(map);
    }

    protected ImmutableMap<K, V> immutableMap() {
        return ImmutableMap.copyOf(this.map());
    }

    protected Collection<V> values() {
        return map.values();
    }

}
