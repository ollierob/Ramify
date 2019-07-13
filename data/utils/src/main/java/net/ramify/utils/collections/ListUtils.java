package net.ramify.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Function;

public class ListUtils {

    public static <F, T> List<T> eagerlyTransform(final Collection<F> collection, final Function<? super F, ? extends T> transform) {
        if (collection.isEmpty()) return Collections.emptyList();
        if (collection instanceof List && collection instanceof RandomAccess) return eagerlyTransform((List<F>) collection, transform);
        final var list = new ArrayList<T>(collection.size());
        for (final var element : collection) {
            list.add(transform.apply(element));
        }
        return list;
    }

    public static <F, T> List<T> eagerlyTransform(final List<F> list, final Function<? super F, ? extends T> transform) {
        if (!(list instanceof RandomAccess)) return eagerlyTransform((Collection<F>) list, transform);
        final var out = new ArrayList<T>(list.size());
        for (int i = 0; i < list.size(); i++) {
            out.add(transform.apply(list.get(i)));
        }
        return out;
    }

}
