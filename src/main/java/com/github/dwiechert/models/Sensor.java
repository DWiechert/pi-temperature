package com.github.dwiechert.models;

/**
 * Class that holds information about a sensor.
 * 
 * @author Dan Wiechert
 */
public class Sensor {
	private String name;
	private String serialId;
	private float tempC;
	private float tempF;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the serialId
	 */
	public String getSerialId() {
		return serialId;
	}

	/**
	 * @param serialId
	 *            the serialId to set
	 */
	public void setSerialId(final String serialId) {
		this.serialId = serialId;
	}

	/**
	 * @return the tempC
	 */
	public float getTempC() {
		return tempC;
	}

	/**
	 * @param tempC
	 *            the tempC to set
	 */
	public void setTempC(final float tempC) {
		this.tempC = tempC;
	}

	/**
	 * @return the tempF
	 */
	public float getTempF() {
		return tempF;
	}

	/**
	 * @param tempF
	 *            the tempF to set
	 */
	public void setTempF(final float tempF) {
		this.tempF = tempF;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (serialId == null ? 0 : serialId.hashCode());
		result = prime * result + Float.floatToIntBits(tempC);
		result = prime * result + Float.floatToIntBits(tempF);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Sensor)) {
			return false;
		}
		final Sensor other = (Sensor) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (serialId == null) {
			if (other.serialId != null) {
				return false;
			}
		} else if (!serialId.equals(other.serialId)) {
			return false;
		}
		if (Float.floatToIntBits(tempC) != Float.floatToIntBits(other.tempC)) {
			return false;
		}
		if (Float.floatToIntBits(tempF) != Float.floatToIntBits(other.tempF)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sensor [name=" + name + ", serialId=" + serialId + ", tempC=" + tempC + ", tempF=" + tempF + "]";
	}
}
