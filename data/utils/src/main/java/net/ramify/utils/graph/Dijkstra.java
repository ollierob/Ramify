package net.ramify.utils.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.graph.Network;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Dijkstra {

    static <N, E> List<E> findPath(final Network<N, E> network, final N from, final N to) {

        final var dist = new DistMap<>(from);
        final var prev = Maps.<N, N>newHashMap();
        final var remaining = Sets.newHashSet(network.nodes());

        for (final var node : network.nodes()) {

            final var min = dist.min(remaining);
            if (min == null) return Collections.emptyList();

            final var minNode = min.getKey();
            if (to.equals(minNode)) break;

            remaining.remove(minNode);

            final var minDist = min.getValue();

            for (final var neighbour : network.adjacentNodes(minNode)) {

                final var alt = minDist + 1;
                final var neighbourDist = dist.dist(neighbour);
                if (alt < neighbourDist) {
                    dist.put(neighbour, alt);
                    prev.put(neighbour, minNode);
                }

            }

        }

        return formPath(to, prev, network);

    }

    private static final class DistMap<N> extends HashMap<N, Double> {

        DistMap(final N root) {
            this.put(root, 0d);
        }

        double dist(final N node) {
            return this.getOrDefault(node, Double.POSITIVE_INFINITY);
        }

        Entry<N, Double> min(final Set<N> eligible) {
            return this.entrySet()
                    .stream()
                    .filter(e -> eligible.contains(e.getKey()))
                    .min((e1, e2) -> (int) (e1.getValue() - e2.getValue()))
                    .orElse(null);
        }

    }

    private static <N, E> List<E> formPath(final N target, final Map<N, N> prev, final Network<N, E> network) {
        final var path = Lists.<E>newArrayListWithExpectedSize(prev.size());
        var u = target;
        while (u != null) {
            final var v = prev.get(u);
            if (v != null) path.add(0, network.edgeConnecting(v, u).orElse(null));
            u = v;
        }
        return path;
    }

}
