package com.perisatto.fiapprj.menuguru_order.infra.persistences.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payment")
public class PaymentDocument {

    private org.bson.Document json;
	
	public PaymentDocument(org.bson.Document json) {
		this.json = json;
	}

	public org.bson.Document getJson() {
		return json;
	}

	public void setJson(org.bson.Document json) {
		this.json = json;
	}
}
