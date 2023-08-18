package com.demo.diet.service;

import com.demo.diet.model.Product;
import com.demo.diet.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return List.of(
                new Product().setName("Bread"),
                new Product().setName("Milk")
        );
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> getProduct(Integer id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }
}
