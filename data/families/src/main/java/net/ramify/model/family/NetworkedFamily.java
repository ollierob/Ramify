package net.ramify.model.family;

import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.Network;
import net.ramify.model.person.Person;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.Set;

public class NetworkedFamily implements Family {

    private final ImmutableNetwork<Person, Relationship> network;

    public NetworkedFamily(final Network<Person, Relationship> network) {
        this.network = ImmutableNetwork.copyOf(network);
    }

    @Nonnull
    @Override
    public Set<? extends Person> people() {
        return network.nodes();
    }

    @Nonnull
    @Override
    public Set<? extends Relationship> relationships() {
        return network.edges();
    }

    @Nonnull
    @Override
    public ImmutableNetwork<Person, Relationship> asNetwork() {
        return network;
    }

}
