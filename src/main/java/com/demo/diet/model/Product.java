package com.demo.diet.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Product {
    private int id;
    private String name;
    private int kcal;
}
