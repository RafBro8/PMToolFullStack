package com.alpha;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.alpha"})
public class PMToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(PMToolApplication.class, args);
    }
}
