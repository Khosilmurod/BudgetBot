package com.ecma.testdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TestDbApplication {

    public static void main(String[] args) {
//        ApiContextInitializer.init();
        SpringApplication.run(TestDbApplication.class, args);
    }

}
