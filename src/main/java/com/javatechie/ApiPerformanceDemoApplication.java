package com.javatechie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.javatechie.controller", "com.javatechie.service", "com.javatechie.facade"})
public class ApiPerformanceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPerformanceDemoApplication.class, args);
	}

}
