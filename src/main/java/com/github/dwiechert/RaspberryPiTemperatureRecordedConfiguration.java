package com.github.dwiechert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.github.dwiechert.alert.Alert;
import com.github.dwiechert.models.Sensor;

@Configuration
public class RaspberryPiTemperatureRecordedConfiguration {
	@Autowired
	private List<Alert> alerts;

	private List<Sensor> sensors;

	/**
	 * @return the alerts
	 */
	public List<Alert> getAlerts() {
		return alerts;
	}

	/**
	 * @return the sensors
	 */
	public List<Sensor> getSensors() {
		if (sensors == null) {
			sensors = new ArrayList<>();
		}
		return sensors;
	}

	/**
	 * @param sensors
	 *            the sensors to set
	 */
	public void setSensors(final List<Sensor> sensors) {
		this.sensors = Collections.synchronizedList(sensors);
	}
}
