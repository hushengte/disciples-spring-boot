package com.disciples.springboot.autoconfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class TestApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }
    
}
