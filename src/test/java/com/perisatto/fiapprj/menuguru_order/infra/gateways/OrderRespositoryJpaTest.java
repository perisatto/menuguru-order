package com.perisatto.fiapprj.menuguru_order.infra.gateways;

import org.mockito.Mock;

import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.OrderMapper;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories.OrderPersistenceRepository;

public class OrderRespositoryJpaTest {
	
	private OrderRepositoryJpa orderRepositoryJpa;
	
	@Mock
	private OrderPersistenceRepository orderPersistenceRepository;
	
	private OrderMapper orderMapper;
	
	AutoCloseable openMocks;
	
}
