package com.inflearnspringframeworkbasic.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackageClasses = DemoApplication.class)
public class ApplicationConfig {

}


