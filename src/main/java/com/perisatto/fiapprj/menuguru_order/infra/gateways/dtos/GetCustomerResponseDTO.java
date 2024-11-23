package com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos;

public class GetCustomerResponseDTO {
	private Integer id;
	private String documentNumber;
	private String name;
	private String email;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
