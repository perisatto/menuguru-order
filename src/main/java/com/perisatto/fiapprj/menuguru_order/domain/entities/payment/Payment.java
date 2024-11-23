package com.perisatto.fiapprj.menuguru_order.domain.entities.payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;

public class Payment {
	
	static final Logger logger = LogManager.getLogger(Payment.class);
	
	private String id;
	private Order order;
	private String paymentLocation;
		
	public Payment(Order order) {
		this.order = order;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getPaymentLocation() {
		return paymentLocation;
	}
	public void setPaymentLocation(String paymentLocation) {
		this.paymentLocation = paymentLocation;
	}
}
