package com.github.dwiechert.alert;

import java.util.List;

import com.github.dwiechert.models.Sensor;

public interface Alert {
	public void alert(List<Sensor> sensors);
	public void update(String message);
	public void setOn(boolean on);
	public boolean isOn();
	public String getName();
}
