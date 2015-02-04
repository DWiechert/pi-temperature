package com.github.dwiechert.alert.impls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dwiechert.alert.Alert;

public class NoOpAlert implements Alert {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoOpAlert.class);

	@Override
	public void alert() {
		LOGGER.warn("Alert was called on the NoOpAlert implementation.");
	}
}
