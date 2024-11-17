package com.perisatto.fiapprj.menuguru_order.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru_order.domain.entities.customer.CPF;
import com.perisatto.fiapprj.menuguru_order.domain.entities.customer.Customer;

@ActiveProfiles(value = "test")
public class CustomerTest {

	@Test
	void givenValidCPF_thenRegisterCustomer() throws Exception {
		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String documentNumber = "90779778057";

		CPF customerCPF = new CPF(documentNumber);
		Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

		assertThat(customer.getName()).isEqualTo(customerName);
		assertThat(customer.getEmail()).isEqualTo(customerEmail);
		assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
	}

	@Test
	void givenInvalidCPF_thenRefusesToRegisterCustomer() throws Exception {
		try {
			String customerName = "Roberto Machado";
			String customerEmail = "roberto.machado@bestmail.com";
			String documentNumber = "90779778058";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo("Invalid document number");
		}
	}

	@Test
	void givenInvalidEmail_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = "Roberto Machado";
			String customerEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isEqualTo(customerName);
			assertThat(customer.getEmail()).isEqualTo(customerEmail);
			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("invalid e-mail format");
		}
	}
	
	@Test
	void givenEmptyName_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = "";
			String customerEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isEqualTo(customerName);
			assertThat(customer.getEmail()).isEqualTo(customerEmail);
			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("empty, null or blank name");
		}
	}
}
