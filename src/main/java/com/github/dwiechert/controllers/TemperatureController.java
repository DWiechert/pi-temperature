package com.github.dwiechert.controllers;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dwiechert.RaspberryPiTemperatureRecordedConfiguration;

@Controller
@RequestMapping("/temperature")
public class TemperatureController {
	@Autowired
	private RaspberryPiTemperatureRecordedConfiguration configuration;
	
	@RequestMapping(value = "/t", method = RequestMethod.GET)
	public @ResponseBody String t() throws Exception {
		configuration.getAlerts().get(0).setOn(!configuration.getAlerts().get(0).isOn());
		return "asdf";
	}

	@RequestMapping(value = "/c", method = RequestMethod.GET)
	public @ResponseBody String c() throws Exception {
		return getTempC() + "°C";
	}

	@RequestMapping(value = "/f", method = RequestMethod.GET)
	public @ResponseBody String f() throws Exception {
		final float c = getTempC();
		return ((c * (9 / 5.0)) + 32) + "°F";
	}

	private float getTempC() throws Exception {
		// /sys/bus/w1/devices/28-00000656edfa/w1_slave
		final String line = FileUtils.readLines(new File("/sys/bus/w1/devices/28-00000656edfa/w1_slave")).get(1);
		final String tempEqual = line.split(" ")[9];
		final int temp = Integer.parseInt(tempEqual.substring(2));
		return temp / 1000;
	}
}
