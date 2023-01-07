package com.pyramix.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

import com.pyramix.security.config.RsaKeyProperties;

@SpringBootApplication
@ImportResource({
	"classpath*:ApplicationContext-GuiController.xml"})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SpringSecurityJwtBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtBeApplication.class, args);
	}

}
