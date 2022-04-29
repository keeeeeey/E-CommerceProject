package com.example.marketboro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MarketboroApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketboroApplication.class, args);
    }

}
