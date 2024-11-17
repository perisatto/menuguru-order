package com.perisatto.fiapprj.menuguru.application.interfaces;

import java.util.Optional;
import java.util.Set;

import com.perisatto.fiapprj.menuguru.domain.entities.customer.CPF;
import com.perisatto.fiapprj.menuguru.domain.entities.customer.Customer;

public interface CustomerRepository {
	
	Optional<Customer> getCustomerById(Long customerId) throws Exception;
}
