package com.primeholding.todoapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodoapplicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoapplicationApplication.class, args);
    }
}
