package com.demo.diet.model;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ProductDto {

    @NotEmpty(message = "The name cannot be empty.")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;

    @Digits(integer=4, fraction=1, message = "The value must have 4 digits whole number.")
    @Min(value = 0, message = "The value must be positive.")
    private int kcal;

    public Product toProduct() {
        return new Product()
                .setName(name)
                .setKcal(kcal);
    }
}
