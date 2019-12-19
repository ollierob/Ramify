package net.ramify.model.family;

import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.Network;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.computed.RelationshipPath;
import net.ramify.model.relationship.type.Unknown;
import net.ramify.utils.graph.NetworkUtils;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

class NetworkedFamily implements Family {

    private final ImmutableNetwork<Person, Relationship> network;
    private final Person root;

    NetworkedFamily(final Network<Person, Relationship> network, final Person root) {
        this.network = ImmutableNetwork.copyOf(network);
        this.root = root;
    }

    @Nonnull
    @Override
    public Set<? extends Person> people() {
        return network.nodes();
    }

    @Nonnull
    @Override
    public Person root() {
        return root;
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

    @Nonnull
    @Override
    public Optional<Relationship> between(final PersonId from, final PersonId to) {
        final var start = this.find(from).orElse(null);
        if (start == null) return Optional.empty();
        final var end = this.find(to).orElse(null);
        if (end == null) return Optional.empty();
        final var path = NetworkUtils.findPath(network, start, end);
        return Optional.of(path.isEmpty()
                ? new Unknown(from, to)
                : new RelationshipPath(path));
    }

}
