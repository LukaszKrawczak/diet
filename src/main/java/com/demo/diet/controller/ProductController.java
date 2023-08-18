package com.demo.diet.controller;

import com.demo.diet.model.Nutrients;
import com.demo.diet.model.Product;
import com.demo.diet.model.ProductDto;
import com.demo.diet.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productsService;

    @PostMapping("/add")
    public ResponseEntity<?> createProductWrapper(@Valid @RequestBody ProductDto productDto) {
        String productDtoName = productDto.getName();
        boolean productExists = productsService.getProductByName(productDtoName).isPresent();
        if (productExists) {
            return new ResponseEntity<>("Product already exists", HttpStatus.CONFLICT);
        }

        log.info("ProductDto retrieved: " + productDto);
        Product product = productDto.toProduct();
        Nutrients nutrients = productDto.getNutrientsDto().toNutrients();
        nutrients.setProduct(product);
        product.setNutrients(nutrients);
        productsService.saveProduct(product);
        log.info("Product saved successfully {}", product);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer id) {
        Optional<Product> product = productsService.getProduct(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProductDto productDto = ProductDto.fromProduct(product.get());
        log.info("ProductDto retrieved: " + productDto);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
}
