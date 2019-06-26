package com.ecust.touhouairline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

@SpringBootApplication
@EnableJpaRepositories
public class TouhouairlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouhouairlineApplication.class, args);
    }

    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
        return new OpenEntityManagerInViewFilter();
    }
}
