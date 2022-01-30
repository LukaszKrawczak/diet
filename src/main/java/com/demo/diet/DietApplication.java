package com.demo.diet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DietApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DietApplication.class, args);
        System.out.println("tu kornelia");
    }
}

