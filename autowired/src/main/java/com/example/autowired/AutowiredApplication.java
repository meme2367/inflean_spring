package com.example.autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class AutowiredApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutowiredApplication.class, args);
    }
}
