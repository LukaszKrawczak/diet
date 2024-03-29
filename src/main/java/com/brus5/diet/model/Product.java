package com.brus5.diet.model;

import lombok.*;
import lombok.experimental.Accessors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "product_id")
    private Integer id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private Nutrients nutrients;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(nutrients, product.nutrients);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
