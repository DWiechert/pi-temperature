package com.github.dwiechert;

import java.math.BigInteger;
import java.util.BitSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

@Controller
public class TemperatureController {
	// create gpio controller instance
	final GpioController gpio = GpioFactory.getInstance();
	final GpioPinDigitalInput pin1;
	final GpioPinDigitalInput pin2;
	final GpioPinDigitalInput pin3;
	final GpioPinDigitalInput pin4;
	final GpioPinDigitalInput pin5;
	final GpioPinDigitalInput pin6;
	final GpioPinDigitalInput pin7;

	public TemperatureController() {
		pin1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, "pin1");
		pin2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "pin2");
		pin3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "pin3");
		pin4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "pin4");
		pin5 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, "pin5");
		pin6 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, "pin6");
		pin7 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "pin7");
	}

	@RequestMapping(value = "/temperature", method = RequestMethod.GET)
	public @ResponseBody long temperature() {
		final BitSet bits = new BitSet(7);
		if (pin1.isHigh()) bits.flip(0);
		if (pin2.isHigh()) bits.flip(1);
		if (pin3.isHigh()) bits.flip(2);
		if (pin4.isHigh()) bits.flip(3);
		if (pin5.isHigh()) bits.flip(4);
		if (pin6.isHigh()) bits.flip(5);
		if (pin7.isHigh()) bits.flip(6);
		
		return new BigInteger(bits.toByteArray()).intValue();
	}
}
