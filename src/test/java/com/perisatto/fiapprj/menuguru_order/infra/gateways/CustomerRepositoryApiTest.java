package com.perisatto.fiapprj.menuguru_order.infra.gateways;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perisatto.fiapprj.menuguru_order.domain.entities.customer.Customer;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos.GetCustomerResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.CustomerMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class CustomerRepositoryApiTest {
	
	private CustomerRepositoyApi customerRepositoyApi;
	
	public static MockWebServer mockBackEnd;
	
	@Mock
	private Environment env;
	
	private CustomerMapper customerMapper;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() throws IOException {
		openMocks = MockitoAnnotations.openMocks(this);
		customerMapper = new CustomerMapper();
		customerRepositoyApi = new CustomerRepositoyApi(env, customerMapper);
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
  	}
	
	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
		mockBackEnd.shutdown();
	}
	
	@Test
	void givenValidId_thenRetrieveCustomer() throws Exception {
		GetCustomerResponseDTO getCustomerResponseDTO = new GetCustomerResponseDTO();
		getCustomerResponseDTO.setId(10);
		getCustomerResponseDTO.setName("Roberto Machado");
		getCustomerResponseDTO.setEmail("roberto.machado@bestmail.com");
		getCustomerResponseDTO.setDocumentNumber("19844291089");
		
		String serverUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
		
		when(env.getProperty(any(String.class)))
		.thenReturn(serverUrl);
		
		mockBackEnd.enqueue(new MockResponse().setBody(asJsonString(getCustomerResponseDTO))
				.addHeader("Content-Type", "application/json"));
		
		Optional<Customer> customer = customerRepositoyApi.getCustomerById(10L);
		
		assertThat(customer.get().getId()).isEqualTo(Long.parseLong(getCustomerResponseDTO.getId().toString()));
		assertThat(customer.get().getName()).isEqualTo(getCustomerResponseDTO.getName());
		assertThat(customer.get().getEmail()).isEqualTo(getCustomerResponseDTO.getEmail());
		assertThat(customer.get().getDocumentNumber().getDocumentNumber()).isEqualTo(getCustomerResponseDTO.getDocumentNumber());
	}
	
	@Test
	void givenInvalidId_thenRefusesRetrieveCustomer() throws Exception {		
		String serverUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
		
		when(env.getProperty(any(String.class)))
		.thenReturn(serverUrl);
		
		mockBackEnd.enqueue(new MockResponse().setBody("").setResponseCode(404)
				.addHeader("Content-Type", "application/json"));
		
		Optional<Customer> customer = customerRepositoyApi.getCustomerById(10L);
		
		assertThat(customer).isEmpty();
	}
	
	@Test
	void givenServerError_thenRefusesRetrieveCustomer() throws Exception {		
		String serverUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
		
		when(env.getProperty(any(String.class)))
		.thenReturn(serverUrl);
		
		mockBackEnd.enqueue(new MockResponse().setBody("").setResponseCode(500)
				.addHeader("Content-Type", "application/json"));
		
		try {
			customerRepositoyApi.getCustomerById(10L);
		} catch (Exception e) {
			assertThatExceptionOfType(HttpClientErrorException.class);
		}
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
