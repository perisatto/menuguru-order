package com.perisatto.fiapprj.menuguru_order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.perisatto.fiapprj.menuguru_order.infra.gateways.CustomerRepositoryApi;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.CustomerMapper;

@Configuration
public class CustomerConfig {

	@Autowired
	private Environment env;
	
	@Bean
	CustomerRepositoryApi customerRepositoyApi(CustomerMapper customerMapper){ 
		return new CustomerRepositoryApi(this.env, customerMapper);
	}
	
	@Bean
	CustomerMapper customerMapper() {
		return new CustomerMapper();
	}
}
