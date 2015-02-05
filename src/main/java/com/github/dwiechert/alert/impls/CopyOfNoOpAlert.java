package com.github.dwiechert.alert.impls;

import org.springframework.stereotype.Component;

import com.github.dwiechert.alert.AbstractAlert;

@Component
public class CopyOfNoOpAlert extends AbstractAlert {
	private static final String NAME = "CopyOfNoOpAlert";

	@Override
	public void alert(final String message) {
		LOGGER.warn("Alert was called on the {} implementation. IsOn - {}.", NAME, isOn());
	}

	@Override
	public String getName() {
		return NAME;
	}
}
