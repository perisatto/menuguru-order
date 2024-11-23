package com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseQrCodeDTO {
	@JsonProperty("in_store_order_id")
	private String inStoreOrderId;
	@JsonProperty("qr_data")
	private String qrData;
	public String getInStoreOrderId() {
		return inStoreOrderId;
	}
	public void setInStoreOrderId(String inStoreOrderId) {
		this.inStoreOrderId = inStoreOrderId;
	}
	public String getQrData() {
		return qrData;
	}
	public void setQrData(String qrData) {
		this.qrData = qrData;
	}
	
	
}
