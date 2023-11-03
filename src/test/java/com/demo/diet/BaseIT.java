package com.demo.diet;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;


public abstract class BaseIT {

    @ServiceConnection
    private static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.1");

    static {
        mySQLContainer.withUrlParam("serverTimezone", "UTC")
                .withDatabaseName("diet")
                .withUsername("root")
                .withPassword("admin")
                .withReuse(true)
                .start();
    }


}
