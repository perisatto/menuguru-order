package com.perisatto.fiapprj.menuguru_order.infra.gateways;

import java.net.URI;
import java.util.Optional;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.perisatto.fiapprj.menuguru_order.application.interfaces.ProductRepository;
import com.perisatto.fiapprj.menuguru_order.domain.entities.product.Product;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos.GetProductResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.ProductMapper;

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
	public Optional<Product> getProductById(Long productId) throws Exception {
		Product product;

		try {
			String url = env.getProperty("spring.product.serviceUrl") + "/menuguru-products/v1/products/" + productId;
			ResponseEntity<GetProductResponseDTO> response = restClient.get()
					.uri(URI.create(url))
					.accept(MediaType.APPLICATION_JSON)
					.retrieve().toEntity(GetProductResponseDTO.class);

			if(response.getStatusCode() == HttpStatus.OK) {
				product = productMapper.mapToDomainEntity(response.getBody());
				return Optional.of(product);
			}
		}catch (HttpClientErrorException e) {
			HttpStatusCode status = e.getStatusCode();
			if(status.value() == 404) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
		
		return null;
	}
}