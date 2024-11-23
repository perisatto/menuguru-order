package com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestQrCodeItemDTO {
	private String title;	
	@JsonProperty("unit_price")
	private Double unitPrice;
	private Integer quantity;
	@JsonProperty("unit_measure")
	private String unityMeasure;
	@JsonProperty("total_amount")
	private Double totalAmount;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getUnityMeasure() {
		return unityMeasure;
	}
	public void setUnityMeasure(String unityMeasure) {
		this.unityMeasure = unityMeasure;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
