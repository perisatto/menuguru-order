package com.perisatto.fiapprj.menuguru_order.application.interfaces;

import java.util.Optional;
import java.util.Set;

import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;

public interface OrderRepository {
	Order createOrder(Order order) throws Exception;
	
	Optional<Order> getOrder(Long orderId) throws Exception;
	
	Set<Order> findAll(Integer limit, Integer page) throws Exception;
	
	Optional<Order> updateOrder(Order order) throws Exception;
	
	Set<Order> listPreparationQueue(Integer limit, Integer page) throws Exception;
}
