package com.github.dwiechert;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * {@link SpringBootServletInitializer} for the {@code pi-temperature} project.
 * 
 * @author Dan Wiechert
 */
public class ServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(RaspberryPiTemperatureRecorderApplication.class);
	}
}
