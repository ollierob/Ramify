package net.ramify.model.family;

import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import net.ramify.model.person.Person;
import net.ramify.model.relationship.Relationship;

public class FamilyBuilder {

    private final MutableNetwork<Person, Relationship> network = NetworkBuilder.directed().build();

    public FamilyBuilder addPerson(final Person person) {
        network.addNode(person);
        return this;
    }

    public FamilyBuilder addRelationship(final Person from, final Person to, final Relationship relationship) {
        network.addNode(from);
        network.addNode(to);
        network.addEdge(from, to, relationship);
        return this;
    }

    public Family build() {
        //FIXME lazily determine if directed
        return new NetworkedFamily(ImmutableNetwork.copyOf(network));
    }

}
