package com.perisatto.fiapprj.menuguru_order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.perisatto.fiapprj.menuguru_order.infra.gateways.ProductRepositoryApi;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.ProductMapper;

@Configuration
public class ProductConfig {

	@Autowired
	private Environment env;
	
	@Bean
	ProductRepositoryApi productApiRepository(ProductMapper productMapper) { 
		return new ProductRepositoryApi(this.env ,productMapper);
	}
	
	@Bean
	ProductMapper productMapper() {
		return new ProductMapper();
	}
}
