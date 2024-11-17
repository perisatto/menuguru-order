package com.perisatto.fiapprj.menuguru.infra.gateways.mappers;

import com.perisatto.fiapprj.menuguru.domain.entities.customer.CPF;
import com.perisatto.fiapprj.menuguru.domain.entities.customer.Customer;
import com.perisatto.fiapprj.menuguru.infra.gateways.dtos.GetCustomerResponseDTO;

public class CustomerMapper {
	
	public Customer mapToDomainEntity(GetCustomerResponseDTO customer) throws Exception {
		CPF documentNumber = new CPF(customer.getDocumentNumber());
		Customer customerDomainEntity = new Customer(Long.parseLong(customer.getId().toString()), documentNumber, customer.getName(), customer.getEmail());
		return customerDomainEntity;
	}
}
