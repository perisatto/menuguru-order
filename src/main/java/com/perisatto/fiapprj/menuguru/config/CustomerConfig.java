package com.perisatto.fiapprj.menuguru.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.perisatto.fiapprj.menuguru.infra.gateways.CustomerRepositoyApi;
import com.perisatto.fiapprj.menuguru.infra.gateways.mappers.CustomerMapper;

@Configuration
public class CustomerConfig {

	@Autowired
	private Environment env;
	
	@Bean
	CustomerRepositoyApi customerRepositoyApi(CustomerMapper customerMapper){
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder(); 
		return new CustomerRepositoyApi(restTemplateBuilder, this.env, customerMapper);
	}
	
	@Bean
	CustomerMapper customerMapper() {
		return new CustomerMapper();
	}
}
