package com.demo.diet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DietApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietApplication.class, args);
        System.out.println("tu kornelia");
    }
}

