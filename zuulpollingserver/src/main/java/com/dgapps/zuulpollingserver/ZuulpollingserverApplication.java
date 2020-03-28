package com.dgapps.zuulpollingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@SpringBootApplication
@EnableEurekaClient        // It acts as a eureka client
@EnableZuulProxy
public class ZuulpollingserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulpollingserverApplication.class, args);
    }

}
