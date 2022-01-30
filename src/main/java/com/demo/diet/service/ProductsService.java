package com.demo.diet.service;

import com.demo.diet.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    public List<Product> getAllProducts() {
        return List.of(
                new Product().setName("Bread").setKcal(200),
                new Product().setName("Milk").setKcal(300)
        );
    }
}
