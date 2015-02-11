package com.github.dwiechert.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link Alert} which handles changing the status of the alert on and off and provides a default implementation of
 * {@link Alert#getInfo()} .
 * 
 * @author Dan Wiechert
 */
public abstract class AbstractAlert implements Alert {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String INFO_FORMAT = "Name=%-20s InOn=%-5b";
	protected boolean on = false;

	@Override
	public void setOn(boolean on) {
		this.on = on;
	}

	@Override
	public boolean isOn() {
		return on;
	}

	@Override
	public String getInfo() {
		return String.format(INFO_FORMAT, getName(), isOn());
	}
}
