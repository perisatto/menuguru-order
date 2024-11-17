package com.perisatto.fiapprj.menuguru.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.perisatto.fiapprj.menuguru.infra.gateways.ProductRepositoryApi;
import com.perisatto.fiapprj.menuguru.infra.gateways.mappers.ProductMapper;

@Configuration
public class ProductConfig {

	@Autowired
	private Environment env;
	
	@Bean
	ProductRepositoryApi productApiRepository(ProductMapper productMapper) {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder(); 
		return new ProductRepositoryApi(restTemplateBuilder, this.env ,productMapper);
	}
	
	@Bean
	ProductMapper productMapper() {
		return new ProductMapper();
	}
}
