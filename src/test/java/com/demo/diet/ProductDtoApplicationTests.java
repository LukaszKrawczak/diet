package com.demo.diet;


import static org.assertj.core.api.Assertions.assertThat;

import com.demo.diet.controller.DietController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductDtoApplicationTests {

	@Autowired
	private DietController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
