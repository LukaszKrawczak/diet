package com.demo.diet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories
@RestController
public class DietApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DietApplication.class, args);
        System.out.println("tu kornelia");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder
                                                         application) {
        return application.sources(DietApplication.class);
    }

    @RequestMapping(value = "/")
    public String hello() {
        return "Hello World from Tomcat";
    }
}

