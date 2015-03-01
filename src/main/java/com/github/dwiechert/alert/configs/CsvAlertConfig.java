package com.github.dwiechert.alert.configs;

import org.springframework.util.StringUtils;

import com.github.dwiechert.alert.impls.CsvAlert;

/**
 * Config class for the {@link CsvAlert}.
 * 
 * @author Dan Wiechert
 */
public class CsvAlertConfig {
	private static final String DEFAULT_FILENAME = "sensor-data.csv";
	private static final char DEFAULT_DELIMITER = ',';

	private String filename;
	private char delimiter;

	/**
	 * Default constructor for Jackson.
	 */
	public CsvAlertConfig() {
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return StringUtils.hasText(filename) ? filename : DEFAULT_FILENAME;
	}

	/**
	 * @return the delimiter
	 */
	public char getDelimiter() {
		return delimiter == '\u0000' ? DEFAULT_DELIMITER : delimiter;
	}
}
