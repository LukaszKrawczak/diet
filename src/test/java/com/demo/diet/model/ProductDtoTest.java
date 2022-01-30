package com.demo.diet.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    public static final String PROPER_NAME_VALUE = "aa";
    public static final int NEGATIVE_KCAL_VALUE = -1;
    public static final int PROPER_KCAL_VALUE = 100;
    public static final String WRONG_NAME_VALUE = "a";
    private ProductDto objectUnderTest;
    private Validator validator;

    @BeforeEach
    void setup() {
        objectUnderTest = new ProductDto();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldThrowErrorWhenKcalValueHasNegativeValue() {
        // given
        objectUnderTest.setName(PROPER_NAME_VALUE);
        objectUnderTest.setKcal(NEGATIVE_KCAL_VALUE);

        // when
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(objectUnderTest);

        // then
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnNoErrorsWhenProperValueHasBeenGiven() {
        // given
        objectUnderTest.setName(PROPER_NAME_VALUE);
        objectUnderTest.setKcal(PROPER_KCAL_VALUE);

        // when
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(objectUnderTest);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenKcalValueHasMoreThanFourDigits() {
        // given
        int moreThanFourDigits = 44444;
        objectUnderTest.setName(PROPER_NAME_VALUE);
        objectUnderTest.setKcal(moreThanFourDigits);

        // when
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(objectUnderTest);

        // then
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenNameValueIsEmpty() {
        // given
        objectUnderTest.setName("");

        // when
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(objectUnderTest);

        // then
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenNameValueIsNull() {
        // given
        objectUnderTest.setName(null);

        // when
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(objectUnderTest);

        // then
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenNameValueHasMoreThan100Characters() {
        // given
        int invalidMaxCharacters = 101;
        StringBuilder builder = new StringBuilder();
        Stream.iterate(1, i -> i <= invalidMaxCharacters, i -> i + 1)
                .forEach(i -> builder.append("a"));
        objectUnderTest.setName(builder.toString());

        // when
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(objectUnderTest);

        // then
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenNameValueHasLessThan2Characters() {
        // given
        objectUnderTest.setName(WRONG_NAME_VALUE);

        // when
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(objectUnderTest);

        // then
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnTrueWhenBothFieldsAreNotValidated() {
        // given
        objectUnderTest.setName(WRONG_NAME_VALUE);
        objectUnderTest.setKcal(NEGATIVE_KCAL_VALUE);

        // when
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(objectUnderTest);

        // then
        assertTrue(violations.size() > 1);
    }

}