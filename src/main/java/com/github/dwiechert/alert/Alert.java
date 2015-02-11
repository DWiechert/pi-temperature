package com.github.dwiechert.alert;

import java.util.List;

import com.github.dwiechert.models.Sensor;

/**
 * Alerts are a way of notifying a user about a certain temperature. They can be turned on or off, have names, and can be updated. Alerts are updated
 * periodically according to the {@code sensorScanSchedule} (defaults to 1 minute intervals).
 * <p/>
 * To write a custom Alert, just implement this interface and annotate the class with {@code @Component}.
 * 
 * @author Dan Wiechert
 */
public interface Alert {
	/**
	 * Alerts this alert with the provided list of {@link Sensor}s.
	 * 
	 * @param sensors
	 *            The sensors that were read.
	 */
	public void alert(List<Sensor> sensors);

	/**
	 * Updates this alert with the provided message.
	 * 
	 * @param message
	 *            The update message.
	 */
	public void update(String message);

	/**
	 * Sets this alert to the on status. When on, it will be alerted each time the sensors are read.
	 * 
	 * @param on
	 *            {@code True} to set on, {@code false} otherwise.
	 */
	public void setOn(boolean on);

	/**
	 * Sees if this alert is on or not.
	 * 
	 * @return {@code True} if this alert is on, {@code false} otherwise.
	 */
	public boolean isOn();

	/**
	 * Gets the name for this alert.
	 * 
	 * @return The alert's name.
	 */
	public String getName();

	/**
	 * Gets information about this alert.
	 * 
	 * @return The alert's information.
	 */
	public String getInfo();
}
