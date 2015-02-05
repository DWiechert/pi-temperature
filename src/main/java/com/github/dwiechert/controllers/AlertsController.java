package com.github.dwiechert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dwiechert.RaspberryPiTemperatureRecordedConfiguration;
import com.github.dwiechert.alert.Alert;

@Controller
@RequestMapping("/alerts")
public class AlertsController {
	private static final String LIST_FORMAT = "Name=%-20s InOn=%-5b";

	@Autowired
	private RaspberryPiTemperatureRecordedConfiguration configuration;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String listAlerts() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (final Alert alert : configuration.getAlerts()) {
			sb.append("\n\t").append(String.format(LIST_FORMAT, alert.getName(), alert.isOn()));
		}
		sb.append("\n").append("}");
		return sb.toString();
	}
}
