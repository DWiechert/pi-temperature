package com.github.dwiechert.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dwiechert.alert.Alert;
import com.github.dwiechert.models.Greeting;

@Controller
@RequestMapping("/hello-world")
public class HelloWorldController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private Alert alert;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Greeting sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
		alert.alert();
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}