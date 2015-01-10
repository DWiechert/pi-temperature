package com.github.dwiechert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class RaspberryPiTemperatureRecorderApplication {
	public static void main(final String[] args) {
		SpringApplication.run(RaspberryPiTemperatureRecorderApplication.class, args);
	}
}
