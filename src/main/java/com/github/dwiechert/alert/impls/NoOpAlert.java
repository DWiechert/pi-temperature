package com.github.dwiechert.alert.impls;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.dwiechert.alert.AbstractAlert;
import com.github.dwiechert.models.Sensor;

@Component
public class NoOpAlert extends AbstractAlert {
	private static final String NAME = "NoOpAlert";

	@Override
	public void alert(final List<Sensor> sensors) {
		LOGGER.warn("Alert was called on the {} implementation. IsOn - {}.", NAME, isOn());
	}
	
	@Override
	public void update(final String message) {
		LOGGER.warn("Update was called on the {} implemention. Message - {}.", NAME, message);
	}

	@Override
	public String getName() {
		return NAME;
	}
}