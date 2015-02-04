package com.github.dwiechert.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link Alert} which handles turning on and off the alert.
 * 
 * @author Dan Wiechert
 */
public abstract class AbstractAlert implements Alert {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected boolean on = false;

	@Override
	public void setOn(boolean on) {
		this.on = on;
	}

	@Override
	public boolean isOn() {
		return on;
	}
}
