package com.perisatto.fiapprj.menuguru_order.domain.entities.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru_order.handler.exceptions.ValidationException;

public class Customer {	
	
	static final Logger logger = LogManager.getLogger(Customer.class);
	private String messageValidation;
	
	private Long id;
	private String name = null;
	private String email = null;
	private CPF documentNumber;

	public Customer(Long id, CPF documentNumber, String customerName, String customerEmail) throws Exception {
		if(!validate(customerName, customerEmail)) {
			logger.warn("Error validating customer data");
			throw new ValidationException("cstm-2001", messageValidation);
		}
		
		this.id = id;
		this.documentNumber = documentNumber;
		this.name = customerName;
		this.email = customerEmail;	
	}


	private boolean validate(String customerName, String customerEmail) {
		messageValidation = "Error validating customer data: ";
		Boolean valid = true;
		if((customerName == null) || (customerName.isEmpty()) || (customerName.isBlank())) {
			logger.debug("Error validating customer data: empty, null or blank name");
			messageValidation = messageValidation + "empty, null or blank name |";
			valid = false;			
		}

		if((customerEmail == null) || (customerEmail.isEmpty()) || (customerEmail.isBlank())) {
			logger.debug("Error validating customer data: empty, null or blank e-mail");
			messageValidation = messageValidation + "empty, null or blank e-mail | ";
			valid = false;			
		} else if(!customerEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
			logger.debug("Error validating customer data: invalid e-mail format");
			messageValidation = messageValidation + "invalid e-mail format | ";
			valid = false;
		}
		
		return valid;
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


	public void setEmail(String email) {
		this.email = email;
	}


	public CPF getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(CPF documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
}
