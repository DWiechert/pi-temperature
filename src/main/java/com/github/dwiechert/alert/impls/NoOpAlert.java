package com.github.dwiechert.alert.impls;

import org.springframework.stereotype.Component;

import com.github.dwiechert.alert.AbstractAlert;

@Component
public class NoOpAlert extends AbstractAlert {
	private static final String NAME = "NoOpAlert";

	@Override
	public void alert(final String message) {
		LOGGER.warn("Alert was called on the {} implementation. IsOn - {}.", NAME, isOn());
	}

	@Override
	public String name() {
		return NAME;
	}
}