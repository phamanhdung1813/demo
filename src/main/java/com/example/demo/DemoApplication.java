package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadMockData(AppRepository repo) {
        return args -> {
            if (args.length == 0) {
                repo.save(AppEntity.builder().name("Record_A").amount(1000L).build());
            }
        };
    }

}
