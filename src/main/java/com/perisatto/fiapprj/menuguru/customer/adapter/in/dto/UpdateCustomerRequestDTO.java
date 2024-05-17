package com.perisatto.fiapprj.menuguru.customer.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UpdateCustomerRequestDTO {
	private String documentNumber;
	private String name;
	
	@JsonAlias(value = "email")
	private String email;
	
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String eMail) {
		this.email = eMail;
	}
}
