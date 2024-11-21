package com.perisatto.fiapprj.menuguru_order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.perisatto.fiapprj.menuguru_order.infra.gateways.CustomerRepositoyApi;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.CustomerMapper;

@Configuration
public class CustomerConfig {

	@Autowired
	private Environment env;
	
	@Bean
	CustomerRepositoyApi customerRepositoyApi(CustomerMapper customerMapper){ 
		return new CustomerRepositoyApi(this.env, customerMapper);
	}
	
	@Bean
	CustomerMapper customerMapper() {
		return new CustomerMapper();
	}
}
