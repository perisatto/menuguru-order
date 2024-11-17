package com.perisatto.fiapprj.menuguru_order.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru_order.domain.entities.user.User;

@ActiveProfiles(value = "test")
public class UserTest {

	@Test
	void givenValidProperties_thenCreateUser() {
		Long id = 1L;
		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String documentNumber = "90779778057";

		User user = new User();
		user.setEmail(customerEmail);
		user.setId(id);
		user.setName(customerName);
		user.setPassword(documentNumber);
		
		assertThat(user.getName()).isEqualTo(customerName);
		assertThat(user.getEmail()).isEqualTo(customerEmail);
		assertThat(user.getPassword()).isEqualTo(documentNumber);
	}
}
