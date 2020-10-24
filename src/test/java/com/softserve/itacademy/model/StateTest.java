package com.softserve.itacademy.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StateTest {
    private static State validState;

    @BeforeAll
    static void init(){
       validState = new State();
       validState.setName("Valid state");
       validState.getTasks().add(new Task());
    }

    @Test
    void createValidState() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(validState);

        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidStateName")
    void constraintViolationInvalidStateName(String input, String errorValue) {

        State state = new State();
        state.setName(input);
        state.setTasks(validState.getTasks());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidStateName(){
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of(null, null),
                Arguments.of("qwertyuiopasdfghjklzx", "qwertyuiopasdfghjklzx"),
                Arguments.of("qwe$","qwe$")
        );
    }
}
