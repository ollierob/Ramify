package net.ramify.model.person.name;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ForenameSurnameTest {

    @Test
    void testValue() {

        final var name = new ForenameSurname("Rev", Collections.singletonList("Nutcombe"), "Nutcombe", "II");
        assertEquals("Rev Nutcombe Nutcombe II", name.value());

    }

}