package com.github.dwiechert.alert;

import java.beans.PropertyEditorSupport;

public class AlertPropertyEditor extends PropertyEditorSupport {
	@Override
	public String getAsText() {
		return getValue().getClass().toString();
	}

	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		try {
			setValue(Class.forName(text));
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Cannot set <" + text + ">", e);
		}
	}
}
