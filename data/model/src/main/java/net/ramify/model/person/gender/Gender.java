package net.ramify.model.person.gender;

import javax.annotation.Nonnull;

public interface Gender {

    Sex MALE = Sex.MALE;
    Sex FEMALE = Sex.FEMALE;
    Gender UNKNOWN = () -> "Unknown";

    @Nonnull
    String value();

    default boolean equals(final Gender that) {
        return this == that || this.isUnknown() || that.isUnknown();
    }

    default Gender inverse() {
        return this instanceof Sex ? ((Sex) this).inverse() : this;
    }

    default boolean isUnknown() {
        return this == UNKNOWN || "unknown".equalsIgnoreCase(this.value());
    }

}
