package com.perisatto.fiapprj.menuguru_order.application.interfaces;

import java.util.Optional;
import java.util.Set;

import com.perisatto.fiapprj.menuguru_order.domain.entities.customer.CPF;
import com.perisatto.fiapprj.menuguru_order.domain.entities.customer.Customer;

public interface CustomerRepository {
	
	Optional<Customer> getCustomerById(Long customerId) throws Exception;
}
