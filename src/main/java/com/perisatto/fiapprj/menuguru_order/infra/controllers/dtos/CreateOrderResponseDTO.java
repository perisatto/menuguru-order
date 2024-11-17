package com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CreateOrderResponseDTO {
	private Long id;
	private Long customerId;
	private Double totalPrice;
	private String status;
	private String qrData;
	
	@JsonAlias(value = "items")
	private Set<OrderItemDTO> items = new LinkedHashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(Set<OrderItemDTO> items) {
		this.items = items;
	}

	public String getQrData() {
		return qrData;
	}

	public void setQrData(String qrData) {
		this.qrData = qrData;
	}	
}
