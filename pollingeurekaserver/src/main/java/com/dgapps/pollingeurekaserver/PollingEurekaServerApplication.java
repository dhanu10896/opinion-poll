package com.dgapps.pollingeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PollingEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollingEurekaServerApplication.class, args);
    }

}
