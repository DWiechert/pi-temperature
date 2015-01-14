package com.github.dwiechert;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Controller
@RequestMapping("/led")
public class LedController {
	// create gpio controller instance
	final GpioController gpio = GpioFactory.getInstance();
	final GpioPinDigitalOutput myLed;

	public LedController() {
		// pi4j GPIO mappings are NOT the same as on the actual board
		// GPIO_03 maps to GPIO_22 on the board
		// Make sure to consult the pi4j diagram - http://pi4j.com/pins/model-b-plus.html
		// provision gpio pins #04 as an output pin and make sure is is set to LOW at startup
		myLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, // PIN NUMBER
				"My LED", // PIN FRIENDLY NAME (optional)
				PinState.LOW); // PIN STARTUP STATE (optional)
	}

	@RequestMapping(value = "/blink", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void blink() {
		myLed.pulse(1000L);
	}

	@RequestMapping(value = "/low", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void low() {
		myLed.low();
	}

	@RequestMapping(value = "/high", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void high() {
		myLed.high();
	}

	@RequestMapping(value = "/toggle", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void toggle() {
		myLed.toggle();
	}

	@RequestMapping(value = "/pinState", method = RequestMethod.GET)
	public @ResponseBody PinState pinState() {
		return myLed.getState();
	}

	@RequestMapping(value = "/isLow", method = RequestMethod.GET)
	public @ResponseBody boolean isLow() {
		return myLed.getState().isLow();
	}

	@RequestMapping(value = "/isHigh", method = RequestMethod.GET)
	public @ResponseBody boolean isHigh() {
		return myLed.getState().isHigh();
	}
}
