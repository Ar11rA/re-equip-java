package com.example.reactivespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { ReactiveUserDetailsServiceAutoConfiguration.class})
public class ReactiveSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringApplication.class, args);
    }

}
