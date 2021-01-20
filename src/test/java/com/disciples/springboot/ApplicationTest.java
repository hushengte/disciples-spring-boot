package com.disciples.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.disciples.springboot.autoconfigure.iam.IamMvcAutoConfiguration;
import com.disciples.springboot.autoconfigure.iam.IamOAuth2AutoConfiguration;

@SpringBootApplication(exclude = {IamMvcAutoConfiguration.class, IamOAuth2AutoConfiguration.class})
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
    
}
