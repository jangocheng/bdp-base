package com.platform.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class ReportSystemApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportSystemApiApplication.class, args);
    }
}
