package com.github.dwiechert.alert;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.stereotype.Component;

// FIXME: Probably don't need this class
@Component
public class AlertEditorRegistrar implements PropertyEditorRegistrar {
	@Override
	public void registerCustomEditors(final PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Alert.class, new AlertPropertyEditor());
	}
}
