package com.perisatto.fiapprj.menuguru_order.application.usecases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru_order.application.interfaces.CustomerRepository;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.OrderRepository;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.PaymentProcessor;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.PaymentRepository;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.ProductRepository;


@ActiveProfiles(value = "test")
public class OrderUseCaseTest {
	
	private OrderUseCase orderUseCase;

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock	
	private PaymentProcessor paymentProcessor; 
	
	@Mock
	private PaymentRepository paymentRepository;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		orderUseCase = new OrderUseCase(orderRepository, customerRepository, productRepository, paymentProcessor, paymentRepository);
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}
	
	@Nested
	class CreateOrder {
		
		@Test
		void givenValidData_thenCreatesOrder() {
			
		}
	}
}
