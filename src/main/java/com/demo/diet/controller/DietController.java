package com.demo.diet.controller;

import com.demo.diet.service.ProductsService;
import com.demo.diet.model.Product;
import com.demo.diet.model.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class DietController {

    private ProductsService productsService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getProducts() {
        return productsService.getAllProducts();
    }

    @PostMapping(value = "/add-product")
    public ResponseEntity<Product> putProduct(@Valid @RequestBody ProductDto productDto) {
        Product addedProduct = productDto.toProduct();
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }
}
