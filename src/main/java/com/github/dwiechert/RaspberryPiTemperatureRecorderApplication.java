package com.github.dwiechert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Main entry point into the {@code pi-temperature} project.
 * 
 * @author Dan Wiechert
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class RaspberryPiTemperatureRecorderApplication {
	/**
	 * Main method.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(final String[] args) {
		SpringApplication.run(RaspberryPiTemperatureRecorderApplication.class, args);
	}
}
