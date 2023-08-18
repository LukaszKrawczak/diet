package com.demo.diet;


import static org.assertj.core.api.Assertions.assertThat;

import com.demo.diet.controller.DietController;
import com.demo.diet.service.ProductsService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class ProductDtoApplicationTests {

	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.29")
            .withUsername("XXXX")
            .withPassword("XXXX");

	@Autowired
	private DietController controller;


	@Autowired
	ProductsService productsService;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void testSaveProduct() {
		productsService.getAllProducts();
	}

}
