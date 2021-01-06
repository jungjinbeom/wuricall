package com.wurigo.socialService;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WurigoServiceApplication extends SpringBootServletInitializer {


  // War Packaging
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(WurigoServiceApplication.class);
  }
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WurigoServiceApplication.class);
		app.run(args);
	}

}
