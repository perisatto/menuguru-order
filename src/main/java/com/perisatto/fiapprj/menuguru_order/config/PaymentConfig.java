package com.perisatto.fiapprj.menuguru_order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.perisatto.fiapprj.menuguru_order.application.interfaces.PaymentProcessor;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.PaymentRepository;
import com.perisatto.fiapprj.menuguru_order.application.usecases.PaymentUseCase;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.PaymentMongoRepository;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.PaymentWebApi;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories.PaymentPersistenceRepository;

@Configuration
public class PaymentConfig {

	@Autowired
	private Environment env;

	@Bean
	PaymentUseCase paymentUseCase(PaymentProcessor paymentProcessor, PaymentRepository paymentRepository) {
		return new PaymentUseCase(paymentProcessor, paymentRepository);
	}

	@Bean
	PaymentWebApi paymentWebApi(){ 
		return new PaymentWebApi(this.env);
	}

	@Bean
	PaymentMongoRepository paymentMongoRepository(PaymentPersistenceRepository paymentPersistenceRepository) {
		PaymentMongoRepository paymentMongoRepository = new PaymentMongoRepository(paymentPersistenceRepository);
		return paymentMongoRepository;
	}
	
}
