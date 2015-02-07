package com.github.dwiechert.controllers;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dwiechert.RaspberryPiTemperatureRecordedConfiguration;
import com.github.dwiechert.alert.Alert;
import com.github.dwiechert.models.Sensor;

@Controller
@EnableScheduling
@RequestMapping("/sensors")
public class SensorsController {
	private static final DecimalFormat FORMAT = new DecimalFormat("#.###");
	private static final String SENSORS_MASTER_DIRECTORY = "/sys/bus/w1/devices/w1_bus_master1/";

	@Autowired
	private RaspberryPiTemperatureRecordedConfiguration configuration;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Sensor> list() throws Exception {
		return readSensors();
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
			final float tempC = readTempC(SENSORS_MASTER_DIRECTORY + serialId + "/w1_slave");
			final float tempF = ((tempC * (9 / 5.0f)) + 32);
			final Sensor sensor = new Sensor();
			sensor.setTempC(Float.valueOf(FORMAT.format(tempC)));
			sensor.setTempF(Float.valueOf(FORMAT.format(tempF)));
			// TODO: Need to implement map of SerialID -> Name
			sensor.setSerialId(serialId);
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
}
