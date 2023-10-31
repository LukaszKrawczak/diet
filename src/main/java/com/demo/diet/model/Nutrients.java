package com.demo.diet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Getter
@Setter
@Accessors(chain = true)
@Entity(name = "nutrients")
public class Nutrients {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "nutrients_id")
    private Integer id;
    private Integer proteins;
    private Integer carbohydrates;
    private Integer fats;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Nutrients(Integer proteins, Integer carbohydrates, Integer fats) {
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }

    public Nutrients() {
    }
}
