package com.github.dwiechert.alert;

public interface Alert {
	public void alert(String message);
	public void setOn(boolean on);
	public boolean isOn();
	public String name();
}
