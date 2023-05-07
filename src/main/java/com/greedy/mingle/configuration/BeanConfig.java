package com.greedy.mingle.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	/* pom.xml에 ModelMapper 의존성 주입 후, config */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
