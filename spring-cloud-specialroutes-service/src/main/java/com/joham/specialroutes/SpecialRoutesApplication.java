package com.joham.specialroutes;

import com.joham.specialroutes.utils.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

/**
 * @author joham
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class SpecialRoutesApplication {

    @Bean
    public Filter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        return userContextFilter;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpecialRoutesApplication.class, args);
    }
}
