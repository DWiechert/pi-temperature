package com.github.dwiechert.controllers;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
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

/**
 * {@link Controller} that handles the REST endpoints for the {@link Sensor}s.
 * 
 * @author Dan Wiechert
 */
@Controller
@EnableAsync
@EnableScheduling
@RequestMapping("/sensors")
public class SensorsController {
	private static final DecimalFormat FORMAT = new DecimalFormat("#.###");
	private static final Map<String, String> SERIAL_NAME_MAP = new ConcurrentHashMap<>();
	// Lock to synchronize on - http://stackoverflow.com/a/5861918/864369
	private final Object LOCK = new Object();

	@Value("${sensorsMasterDirectory:/sys/bus/w1/devices/w1_bus_master1/}")
	private String SENSORS_MASTER_DIRECTORY;

	@Autowired
	private RaspberryPiTemperatureRecordedConfiguration configuration;

	/**
	 * Lists all of the {@link Sensor}s that have been read.
	 * 
	 * @return The list of sensors.
	 * @throws Exception
	 *             If there was an error reading the sensor files.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Sensor> list() throws Exception {
		return getSensors(null);
	}

	/**
	 * Lists all of the {@link Sensor}s that have been read with the provided name.
	 * 
	 * @param name
	 *            The name of the alert.
	 * @return The list of sensors.
	 * @throws Exception
	 *             If there was an error reading the sensor files.
	 */
	@RequestMapping(value = "/list/{name}", method = RequestMethod.GET)
	public @ResponseBody List<Sensor> list(@PathVariable(value = "name") final String name) throws Exception {
		return getSensors(name == null ? "" : name);
	}

	private List<Sensor> getSensors(final String name) throws Exception {
		final List<Sensor> sensors = readSensors();
		final List<Sensor> filteredSensors = new ArrayList<>();
		for (final Sensor sensor : sensors) {
			if (name == null || name.equals(sensor.getName())) {
				filteredSensors.add(sensor);
			}
		}
		return filteredSensors;
	}

	/**
	 * Updates the provided sensor with a user-friendly name.
	 * 
	 * @param serialId
	 *            The serial id of the sensor.
	 * @param name
	 *            The sensor's user-friendly name.
	 */
	@RequestMapping(value = "/name/{serialId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void name(@PathVariable(value = "serialId") final String serialId, @RequestBody final String name) {
		SERIAL_NAME_MAP.put(serialId, name == null ? "" : name);
	}

	/**
	 * Reads the sensors and runs the alerts on a schedule. Defaults to reading every minute, but can be overridden by providing the property
	 * {@code sensorScanSchedule}.
	 * 
	 * @throws Exception
	 *             If there was an error reading the sensor files.
	 */
	@Scheduled(cron = "${sensorScanSchedule:0 0/1 * * * ?}")
	public void runAlerts() throws Exception {
		final List<Sensor> sensors = readSensors();
		for (final Alert alert : configuration.getAlerts()) {
			alert.alert(sensors);
		}
	}

	/**
	 * Reads the sensor files and returns the list of sensors and their temperatures.
	 * 
	 * @return The list of sensors that were read.
	 * @throws Exception
	 *             If there was an error reading the sensor files.
	 */
	private List<Sensor> readSensors() throws Exception {
		final List<Sensor> sensors = new ArrayList<>();
		synchronized (LOCK) {
			final Map<String, String> previousMap = new HashMap<String, String>(SERIAL_NAME_MAP);
			SERIAL_NAME_MAP.clear();

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
				SERIAL_NAME_MAP.put(serialId, previousMap.containsKey(serialId) ? previousMap.get(serialId) : "");
				final float tempC = readTempC(SENSORS_MASTER_DIRECTORY + serialId + File.separatorChar + "w1_slave");
				final float tempF = ((tempC * (9 / 5.0f)) + 32);
				final Sensor sensor = new Sensor();
				sensor.setTempC(Float.valueOf(FORMAT.format(tempC)));
				sensor.setTempF(Float.valueOf(FORMAT.format(tempF)));
				sensor.setSerialId(serialId);
				sensor.setName(SERIAL_NAME_MAP.get(serialId));
				sensors.add(sensor);
			}
		}

		return sensors;
	}

	/**
	 * Reads the temperature from the provided file in Celsius.
	 * 
	 * @param location
	 *            The file location.
	 * @return The Celsius temperature.
	 * @throws Exception
	 *             If there was an error reading the sensor file.
	 */
	private float readTempC(final String location) throws Exception {
		final String line = FileUtils.readLines(new File(location)).get(1);
		final String tempEqual = line.split(" ")[9];
		final int temp = Integer.parseInt(tempEqual.substring(2));
		return temp / 1000f;
	}
}
