package com.demo.diet.model;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


@Data
@Builder
public class NutrientsDto {
    @NotNull(message = "Proteins cannot be null.")
    @PositiveOrZero(message = "Proteins must be positive or zero.")
    @Max(value = 9999, message = "Proteins must be less than or equal to 9999.")
    private Integer proteins;

    @NotNull(message = "Carbohydrates cannot be null.")
    @PositiveOrZero(message = "Carbohydrates must be positive or zero.")
    @Max(value = 9999, message = "Carbohydrates must be less than or equal to 9999.")
    private Integer carbohydrates;

    @NotNull(message = "Fats cannot be null.")
    @PositiveOrZero(message = "Fats must be positive or zero.")
    @Max(value = 9999, message = "Fats must be less than or equal to 9999.")
    private Integer fats;

    public Nutrients toNutrients() {
        return new Nutrients(proteins, carbohydrates, fats);

    }
}
