package com.ecust.touhouairline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TouhouairlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouhouairlineApplication.class, args);
    }
    //test

}
