package com.greedy.mingle.configuration;

import java.io.IOException;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class BeanConfig {

	/* pom.xml에 ModelMapper 의존성 주입 후, config */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	    resolver.setMaxUploadSize(10485760); // 최대 업로드 크기 설정 (10MB)
	    return resolver;
	}
	 
}
