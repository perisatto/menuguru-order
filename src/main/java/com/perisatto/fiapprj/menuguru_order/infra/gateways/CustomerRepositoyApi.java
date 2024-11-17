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

import com.perisatto.fiapprj.menuguru_order.application.interfaces.CustomerRepository;
import com.perisatto.fiapprj.menuguru_order.domain.entities.customer.Customer;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos.GetCustomerResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.CustomerMapper;

public class CustomerRepositoyApi implements CustomerRepository {


	private final RestClient restClient;
	private final Environment env;
	private final CustomerMapper customerMapper;

	public CustomerRepositoyApi(RestTemplateBuilder restTemplateBuilder, Environment env, CustomerMapper customerMapper) {
		this.restClient = RestClient.create();
		this.env = env;
		this.customerMapper = customerMapper;
	}

	public Optional<Customer> getCustomerById(Long customerId) throws Exception{
		Customer customer;

		String url = env.getProperty("spring.customer.serviceUrl") + "/menuguru-customers/v1/customers/" + customerId;
		try {
			ResponseEntity<GetCustomerResponseDTO> response = restClient.get()
					.uri(URI.create(url))
					.accept(MediaType.APPLICATION_JSON)
					.retrieve().toEntity(GetCustomerResponseDTO.class);

			if(response.getStatusCode() == HttpStatus.OK) {
				customer = customerMapper.mapToDomainEntity(response.getBody());
				return Optional.of(customer);
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
