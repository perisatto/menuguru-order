package com.perisatto.fiapprj.menuguru_order.application.interfaces;

import com.perisatto.fiapprj.menuguru_order.domain.entities.payment.Payment;

public interface PaymentProcessor {
	Payment createPayment(Payment payment) throws Exception;
}
