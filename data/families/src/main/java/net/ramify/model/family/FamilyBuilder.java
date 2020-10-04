package net.ramify.model.family;

import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import net.ramify.model.person.Person;
import net.ramify.model.person.provider.PersonProvider;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.RelationshipFactory;

public class FamilyBuilder {

    private final MutableNetwork<Person, Relationship> network = NetworkBuilder.directed().build();
    private Person root;

    public FamilyBuilder() {
    }

    public FamilyBuilder(final Person root) {
        this.root = root;
        network.addNode(root);
    }

    public FamilyBuilder addPerson(final Person person) {
        network.addNode(person);
        if (root == null) root = person;
        return this;
    }

    public FamilyBuilder addRelationship(final Person from, final Person to, final RelationshipFactory factory) {
        return this.addRelationship(from, to, factory.relationshipBetween(from, to));
    }

    public FamilyBuilder addRelationship(final Relationship relationship, final PersonProvider people) {
        return this.addRelationship(people.require(relationship.fromId()), people.require(relationship.toId()), relationship);
    }

    private FamilyBuilder addRelationship(final Person from, final Person to, final Relationship relationship) {
        if (from == to) return this;
        this.addPerson(from);
        this.addPerson(to);
        network.addEdge(from, to, relationship);
        return this;
    }

    public FamilyBuilder addFamily(final Family family, final Relationship join) {
        throw new UnsupportedOperationException();
    }

    public Family build() {
        if (network.nodes().size() == 1) return new SinglePersonFamily(network.nodes().iterator().next());
        //FIXME lazily determine if directed
        return new NetworkedFamily(ImmutableNetwork.copyOf(network), root);
    }

}
