package com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestQrCodeDTO {
	private String description;
	
	@JsonProperty("external_reference")
	private String externalReference;
	
	@JsonProperty("expiration_date")
	private String expirationDate;
	
	private Set<RequestQrCodeItemDTO> items = new LinkedHashSet<RequestQrCodeItemDTO>();
	
	@JsonProperty("notification_url")
	private String notificationUrl;
	
	private String title;
	
	@JsonProperty("total_amount")
	private Double totalAmount;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(String externalRefernce) {
		this.externalReference = externalRefernce;
	}

	public Set<RequestQrCodeItemDTO> getItems() {
		return items;
	}

	public void setItems(Set<RequestQrCodeItemDTO> items) {
		this.items = items;
	}
	
	public String getNotificationUrl() {
		return notificationUrl;
	}

	public void setNotificationUrl(String notificationUrl) {
		this.notificationUrl = notificationUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
		
}
