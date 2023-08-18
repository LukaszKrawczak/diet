package com.demo.diet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Getter
@Setter
@ToString
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
