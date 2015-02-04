package com.github.dwiechert;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.github.dwiechert.alert.Alert;

@Configuration
public class RaspberryPiTemperatureRecordedConfiguration {
	@Autowired
	private List<Alert> alerts;

	/**
	 * @return the alerts
	 */
	public List<Alert> getAlerts() {
		return alerts;
	}
}
