package com.perisatto.fiapprj.menuguru_order.infra.gateways;

import org.bson.Document;

import com.perisatto.fiapprj.menuguru_order.application.interfaces.PaymentRepository;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.PaymentDocument;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories.PaymentPersistenceRepository;

public class PaymentMongoRepository implements PaymentRepository{

	private PaymentPersistenceRepository paymentRepository;
	
	public PaymentMongoRepository(PaymentPersistenceRepository paymentPersistenceRepository) {
		this.paymentRepository = paymentPersistenceRepository;
	}
	
	public Boolean registerPayment(String paymentData) {
		Document document = Document.parse(paymentData);		
		PaymentDocument paymentDocument = new PaymentDocument(document);
		paymentRepository.save(paymentDocument);
		return true;
	}

}
