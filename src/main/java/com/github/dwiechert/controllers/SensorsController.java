package com.github.dwiechert.controllers;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.dwiechert.RaspberryPiTemperatureRecordedConfiguration;
import com.github.dwiechert.alert.Alert;
import com.github.dwiechert.models.Sensor;

@Controller
@EnableScheduling
@RequestMapping("/sensors")
public class SensorsController {
	private static final DecimalFormat FORMAT = new DecimalFormat("#.###");
	private static final String SENSORS_MASTER_DIRECTORY = "/sys/bus/w1/devices/w1_bus_master1/";
	private static final Map<String, String> SERIAL_NAME_MAP = new ConcurrentHashMap<>();

	@Autowired
	private RaspberryPiTemperatureRecordedConfiguration configuration;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Sensor> list() throws Exception {
		return readSensors();
	}

	@RequestMapping(value = "/update/{serialId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@PathVariable(value = "serialId") final String serialId, @RequestBody final String name) {
		updateSerialName(serialId, name);
	}

	@Scheduled(cron = "${sensorScanSchedule:0 0/1 * * * ?}")
	public void runAlerts() throws Exception {
		final List<Sensor> sensors = readSensors();
		for (final Alert alert : configuration.getAlerts()) {
			alert.alert(sensors);
		}
	}

	private List<Sensor> readSensors() throws Exception {
		final List<Sensor> sensors = new ArrayList<>();
		for (final File file : new File(SENSORS_MASTER_DIRECTORY).listFiles()) {
			if (!file.isDirectory()) {
				continue;
			}

			final String filename = file.getName();

			if ("subsystem".equals(filename)) {
				continue;
			}

			if ("driver".equals(filename)) {
				continue;
			}

			if ("power".equals(filename)) {
				continue;
			}

			final String serialId = filename;
			addSerialName(serialId);
			final float tempC = readTempC(SENSORS_MASTER_DIRECTORY + serialId + "/w1_slave");
			final float tempF = ((tempC * (9 / 5.0f)) + 32);
			final Sensor sensor = new Sensor();
			sensor.setTempC(Float.valueOf(FORMAT.format(tempC)));
			sensor.setTempF(Float.valueOf(FORMAT.format(tempF)));
			sensor.setSerialId(serialId);
			sensor.setName(SERIAL_NAME_MAP.get(serialId));
			sensors.add(sensor);
		}

		return sensors;
	}

	private float readTempC(final String location) throws Exception {
		final String line = FileUtils.readLines(new File(location)).get(1);
		final String tempEqual = line.split(" ")[9];
		final int temp = Integer.parseInt(tempEqual.substring(2));
		return temp / 1000f;
	}

	private synchronized void addSerialName(final String serialId) {
		if (!SERIAL_NAME_MAP.containsKey(serialId)) {
			SERIAL_NAME_MAP.put(serialId, "");
		}
	}

	private synchronized void updateSerialName(final String serialId, final String name) {
		SERIAL_NAME_MAP.put(serialId, name == null ? "" : name);
	}
}
