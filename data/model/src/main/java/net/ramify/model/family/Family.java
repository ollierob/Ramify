package net.ramify.model.family;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.graph.Network;
import com.google.common.graph.NetworkBuilder;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.collection.HasPeople;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.collection.HasRelationships;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Family extends HasPeople, HasRelationships {

    @Nonnull
    @Override
    Set<? extends Person> people();

    @Nonnull
    @Override
    Set<? extends Relationship> relationships();

    @Nonnull
    default Network<Person, Relationship> asNetwork() {
        final var directed = Iterables.any(this.relationships(), Relationship::isDirected);
        final var network = (directed ? NetworkBuilder.directed() : NetworkBuilder.undirected()).<Person, Relationship>build();
        final var mapped = Maps.<PersonId, Person>newHashMap();
        this.people().forEach(network::addNode);
        this.relationships().forEach(r -> network.addEdge(mapped.get(r.from()), mapped.get(r.to()), r)); //TODO also add inverses?
        return network;
    }

}