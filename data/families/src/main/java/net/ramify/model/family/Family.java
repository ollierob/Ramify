package net.ramify.model.family;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.graph.Network;
import com.google.common.graph.NetworkBuilder;
import net.meerkat.functions.consumers.Consumers;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.family.proto.FamilyProto;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.collection.HasPeople;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.collection.HasRelationships;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public interface Family extends HasPeople, HasRelationships, BuildsProto<FamilyProto.Family> {

    @Nonnull
    @Override
    Set<? extends Person> people();

    @Nonnull
    Person root();

    @Nonnull
    @Override
    Set<? extends Relationship> relationships();

    @Nonnull
    Optional<Relationship> relationshipBetween(PersonId from, PersonId to);

    @Nonnull
    default Optional<Relationship> relationshipBetween(final HasPersonId from, final HasPersonId to) {
        return this.relationshipBetween(from.personId(), to.personId());
    }

    default boolean hasDirected() {
        return Iterables.any(this.relationships(), Relationship::isDirected);
    }

    @Nonnull
    default Network<Person, Relationship> asNetwork() {
        final var network = (this.hasDirected() ? NetworkBuilder.directed() : NetworkBuilder.undirected()).<Person, Relationship>build();
        final var mapped = Maps.<PersonId, Person>newHashMap();
        this.people().forEach(person -> {
            mapped.put(person.personId(), person);
            network.addNode(person);
        });
        this.relationships().forEach(r -> network.addEdge(mapped.get(r.fromId()), mapped.get(r.toId()), r));
        return network;
    }

    @Nonnull
    default FamilyProto.Family.Builder toProtoBuilder() {
        final var builder = FamilyProto.Family.newBuilder()
                .addAllPerson(Iterables.transform(this.people(), Person::toProto))
                .addAllRelationship(Iterables.transform(this.relationships(), Relationship::toProto));
        Consumers.ifNonNull(this.root(), root -> builder.setRoot(root.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default FamilyProto.Family toProto() {
        return this.toProtoBuilder().build();
    }

}
