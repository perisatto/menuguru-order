package com.perisatto.fiapprj.menuguru_order.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru_order.infra.gateways.PaymentMongoRepository;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories.PaymentPersistenceRepository;

@SpringBootTest
@ActiveProfiles(value = "test")
public class PaymentMongoRepositoryTest {
	
	private PaymentMongoRepository paymentMongoRepository;
	
	@Mock
	private PaymentPersistenceRepository paymentRepository;	
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		paymentMongoRepository = new PaymentMongoRepository(paymentRepository);
	}
	
	@Test
	void givenStringData_thenRegisterPayment() {
		String paymentData = "{\"action\":\"payment.updated\",\"api_version\":\"v1\",\"data\":{\"id\":\"88436747797\"},\"date_created\":\"2024-09-25T01:28:07Z\",\"id\":116049466094,\"live_mode\":true,\"type\":\"payment\",\"user_id\":\"1891840516\"}";
		
		when(paymentRepository.save(any()))
		.thenReturn(null);
		
		Boolean result = paymentMongoRepository.registerPayment(paymentData);
		
		assertThat(result).isTrue();
		
	}
}
