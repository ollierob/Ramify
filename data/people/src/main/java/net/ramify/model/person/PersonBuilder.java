package net.ramify.model.person;

import javax.annotation.CheckReturnValue;
import java.util.Set;

public interface PersonBuilder extends Person {

    @CheckReturnValue
    default PersonBuilder withNotes(final String notes) {
        return notes == null
                ? this
                : new NotedPerson(this, notes);
    }

    @CheckReturnValue
    default PersonBuilder withLabels(final Set<String> labels) {
        return labels == null || labels.isEmpty()
                ? this
                : new LabelledPerson(this, labels);
    }

}
