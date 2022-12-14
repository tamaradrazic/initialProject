package com.example.projekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProjekatApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProjekatApplication.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProjekatApplication.class);
    }
	
	private static Class<ProjekatApplication> applicationClass = ProjekatApplication.class;
}
