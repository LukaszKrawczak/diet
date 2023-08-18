package com.demo.diet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class ProductDto {

    @NotEmpty(message = "The name cannot be empty.")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;

    @Valid
    @JsonProperty("nutrients")
    private NutrientsDto nutrientsDto;

    public static ProductDto fromProduct(Product product) {
        Nutrients nutrients = product.getNutrients();
        NutrientsDto nutrientsDto = new NutrientsDto.NutrientsDtoBuilder()
                .proteins(nutrients.getProteins())
                .carbohydrates(nutrients.getCarbohydrates())
                .fats(nutrients.getFats())
                .build();
        return new ProductDtoBuilder()
                .name(product.getName())
                .nutrientsDto(nutrientsDto)
                .build();

    }

    public Product toProduct() {
        return new Product()
                .setName(name)
                .setNutrients(nutrientsDto.toNutrients());
    }
}
