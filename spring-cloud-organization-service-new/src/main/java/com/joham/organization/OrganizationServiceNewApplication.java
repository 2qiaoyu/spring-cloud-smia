package com.joham.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author joham
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrganizationServiceNewApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrganizationServiceNewApplication.class, args);
    }
}
