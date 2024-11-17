package com.perisatto.fiapprj.menuguru.infra.gateways;

import java.net.URI;
import java.util.Optional;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import com.perisatto.fiapprj.menuguru.application.interfaces.ProductRepository;
import com.perisatto.fiapprj.menuguru.domain.entities.product.Product;
import com.perisatto.fiapprj.menuguru.infra.gateways.dtos.GetProductResponseDTO;
import com.perisatto.fiapprj.menuguru.infra.gateways.mappers.ProductMapper;

public class ProductRepositoryApi implements ProductRepository {
	
	private final RestClient restClient;
	private final Environment env;
	private final ProductMapper productMapper;

	public ProductRepositoryApi(RestTemplateBuilder restTemplateBuilder, Environment env, ProductMapper productMapper) {
		this.restClient = RestClient.create();
		this.env = env;
		this.productMapper = productMapper;
	}

	@Override
	public Optional<Product> getProductById(Long id) throws Exception {
		Product product;

		String url = env.getProperty("spring.product.serviceUrl");
		ResponseEntity<GetProductResponseDTO> response = restClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().toEntity(GetProductResponseDTO.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			product = productMapper.mapToDomainEntity(response.getBody());
		}else {
			return Optional.empty();
		}

		return Optional.of(product);
	}
}
