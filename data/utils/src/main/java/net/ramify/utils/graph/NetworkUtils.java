package net.ramify.utils.graph;

import com.google.common.graph.Network;

import java.util.Collections;
import java.util.List;

public class NetworkUtils {

    public static <N, E> List<E> findPath(final Network<N, E> network, final N from, final N to) {
        final var nodes = network.nodes();
        if (!nodes.contains(from) || !nodes.contains(to) || to.equals(from)) return Collections.emptyList();
        return Dijkstra.findPath(network, from, to);
    }

}
