package com.perisatto.fiapprj.menuguru_order.infra.gateways;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderStatus;
import com.perisatto.fiapprj.menuguru_order.domain.entities.payment.Payment;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos.ResponseQrCodeDTO;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class PaymentWebApiTest {
	
	private PaymentWebApi paymentWebApi;
	
	public static MockWebServer mockBackEnd;
	
	@Mock
	private Environment env;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() throws IOException {
		openMocks = MockitoAnnotations.openMocks(this);
		paymentWebApi = new PaymentWebApi(env);
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
  	}
	
	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
		mockBackEnd.shutdown();
	}
	
	@Test
	void givenValidData_thenRegisterPayment() throws Exception {
		ResponseQrCodeDTO responseQrCodeDTO = new ResponseQrCodeDTO();
		responseQrCodeDTO.setQrData("000000000000000000");
		responseQrCodeDTO.setInStoreOrderId("0000000000000");
		
		String serverUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
		
		when(env.getProperty(any(String.class)))
		.thenReturn(serverUrl);
		
		mockBackEnd.enqueue(new MockResponse().setBody(asJsonString(responseQrCodeDTO))
				.addHeader("Content-Type", "application/json").setResponseCode(201));
		
		Order order = getOrder();
		Payment payment = new Payment(order);
		
		payment = paymentWebApi.createPayment(payment);		
		
	}
	
	public static Order getOrder() throws Exception {
		Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();

		OrderItem orderItem = new OrderItem(10L, 10.0, 1);

		orderItems.add(orderItem);

		Order order = new Order(OrderStatus.EM_PREPARACAO, 10L, orderItems);
		order.setId(10L);
		order.setReadyToPrepare(new Date());
		order.setWaitingTime(Duration.ZERO);
		return order;
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
