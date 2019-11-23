package net.ramify.strategy.merge.person.gender;

import net.ramify.model.person.gender.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultGenderMergerTest {

    private DefaultGenderMerger testMerger;

    @BeforeEach
    void beforeEach() {
        testMerger = new DefaultGenderMerger();
    }

    @Test
    void testMatcher() {

        {
            final var result = testMerger.merge(Gender.UNKNOWN, Gender.UNKNOWN);
            assertEquals(Optional.of(Gender.UNKNOWN), result.value());
        }

        {
            final var result = testMerger.merge(Gender.MALE, Gender.UNKNOWN);
            assertEquals(Optional.of(Gender.MALE), result.value());
        }

        {
            final var result = testMerger.merge(Gender.UNKNOWN, Gender.MALE);
            assertEquals(Optional.of(Gender.MALE), result.value());
        }

        {
            final var result = testMerger.merge(Gender.FEMALE, Gender.MALE);
            assertTrue(result.impossibleMerge());
        }

    }

}