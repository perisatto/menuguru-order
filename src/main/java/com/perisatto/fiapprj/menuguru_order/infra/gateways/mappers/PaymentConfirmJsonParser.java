package com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentConfirmJsonParser {
	
	public Boolean isPaymentCreated(String request) {
		ObjectMapper mapper = new ObjectMapper();
		String paymentAction = "";
		try {
			JsonNode jsonNode = mapper.readTree(request);		
			paymentAction = jsonNode.get("action").textValue();		
		}catch (Exception e) {
			return false;
		}
		if(paymentAction.equals("payment.created")) {
			return true;
		}
		return false;
	}
}
