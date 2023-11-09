package com.demo.diet.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ApiValidationError extends ApiSubError implements Comparable<ApiValidationError>{
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationError() {
        // needed for testing ObjectMapper
    }

    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiValidationError that = (ApiValidationError) o;
        return Objects.equals(object, that.object) && Objects.equals(field, that.field) && Objects.equals(rejectedValue, that.rejectedValue) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, field, rejectedValue, message);
    }

    @Override
    public int compareTo(ApiValidationError otherApiSubError) {
        return this.field.compareTo(otherApiSubError.field);
    }

}
