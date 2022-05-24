package com.epam.springRestExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.epam")
@EnableJpaRepositories(basePackages = "com.epam.repository")
@EntityScan(basePackages = "com.epam.model")
public class SpringRestExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringRestExampleApplication.class, args);
    }
}
