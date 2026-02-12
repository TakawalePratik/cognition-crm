package com.cognition.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CognitionCrmApplication extends SpringBootServletInitializer{
	@Override protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{ return builder.sources(CognitionCrmApplication.class); }

	public static void main(String[] args) {
		System.out.println("Hello");
		SpringApplication.run(CognitionCrmApplication.class, args);
	}

}
