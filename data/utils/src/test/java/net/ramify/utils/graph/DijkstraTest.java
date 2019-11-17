package net.ramify.utils.graph;

import com.google.common.graph.NetworkBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DijkstraTest {

    @Test
    void shouldFindPath() {

        final var network = NetworkBuilder.directed().<String, String>build();

        final var a = "a";
        final var b = "b";
        final var c = "c";
        final var d = "d";

        final var ab = "a->b";
        final var bc = "b->c";
        final var ad = "a->d";

        network.addNode(a);
        network.addNode(b);
        network.addNode(c);
        network.addNode(d);
        network.addEdge(a, b, ab);
        network.addEdge(b, c, bc);
        network.addEdge(a, d, ad);

        final var path = Dijkstra.findPath(network, a, c);
        assertEquals(Arrays.asList(ab, bc), path);

    }

}