package com.github.dwiechert.alert;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

// Found online - http://stackoverflow.com/a/18965511/864369
@Component
public class AlertBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	@Value("${alertBean:noOp}")
	private String alertClass;

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// here, all beans are available including those defined by @configuration, @component, xml, etc.

		// do some magic to somehow find which is the preferred bean name for each interface
		// you have access to all bean-definition names with: beanFactory.getBeanDefinitionNames()
		// get the definition for that bean and set it as primary
		beanFactory.getBeanDefinition(alertClass).setPrimary(true);
	}

	@Override
	public void postProcessBeanDefinitionRegistry(final BeanDefinitionRegistry registry) throws BeansException {
		// this method can be used to set a primary bean, although
		// beans defined in a @Configuration class will not be available here.
	}
}
