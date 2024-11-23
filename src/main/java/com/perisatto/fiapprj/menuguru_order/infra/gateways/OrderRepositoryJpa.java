package com.perisatto.fiapprj.menuguru_order.infra.gateways;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.perisatto.fiapprj.menuguru_order.application.interfaces.OrderRepository;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderStatus;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.OrderMapper;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.OrderEntity;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.OrderItemEntity;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories.OrderPersistenceRepository;


public class OrderRepositoryJpa implements OrderRepository {
	
	private final OrderPersistenceRepository orderRepository;
	private final OrderMapper orderMapper;
	
	public OrderRepositoryJpa(OrderPersistenceRepository orderRepository, OrderMapper orderMapper) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
	}

	public Order createOrder(Order order) throws Exception {
		OrderEntity orderEntity = orderMapper.mapToJpaEntity(order);
		orderEntity = orderRepository.save(orderEntity);
		Order createdOrder = orderMapper.mapToDomainEntity(orderEntity);
		return createdOrder;
	}

	public Optional<Order> getOrder(Long orderId) throws Exception {
		Order order;
		Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
		
		if(orderEntity.isPresent()) {
			order = orderMapper.mapToDomainEntity(orderEntity.get());
		}else {
			return Optional.empty();
		}
		return Optional.of(order);
	}

	public Set<Order> findAll(Integer limit, Integer page) throws Exception {	
		Pageable pageable = PageRequest.of(page, limit, Sort.by("idOrder"));
		Page<OrderEntity> orders;
		
		orders = orderRepository.findAll(pageable);
		
		Set<Order> orderSet = new LinkedHashSet<Order>();
		
			
		for (OrderEntity order : orders) {
			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			for(OrderItemEntity orderItemsJpa : order.getItems()) {
				OrderItem orderItem = new OrderItem(orderItemsJpa.getIdProduct(),orderItemsJpa.getActualPrice(),orderItemsJpa.getQuantity());
				orderItem.setProductId(orderItemsJpa.getIdProduct());
				orderItems.add(orderItem);
			}
			
			Order retrievedOrder = new Order(OrderStatus.values()[(int) (order.getIdOrderStatus() - 1)], order.getIdCustomer(), orderItems);
			retrievedOrder.setId(order.getIdOrder());
			orderSet.add(retrievedOrder);
		}
		return orderSet;
	}

	public Optional<Order> updateOrder(Order order) throws Exception {
		OrderEntity orderEntity = orderMapper.mapToJpaEntity(order);
		orderEntity = orderRepository.save(orderEntity);
		Order updatedOrder = orderMapper.mapToDomainEntity(orderEntity);
		return Optional.of(updatedOrder);
	}

	public Set<Order> listPreparationQueue(Integer limit, Integer page) throws Exception {
		Pageable pageable = PageRequest.of(page, limit, Sort.by("idOrderStatus").descending().and(Sort.by("readyToPrepare")));
		Page<OrderEntity> orders = orderRepository.findByIdOrderStatusBetween(OrderStatus.RECEBIDO.getId(), OrderStatus.PRONTO.getId(), pageable);
		
		Set<Order> orderSet = new LinkedHashSet<Order>();
		
			
		for (OrderEntity order : orders) {
			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			for(OrderItemEntity orderItemsJpa : order.getItems()) {
				OrderItem orderItem = new OrderItem(orderItemsJpa.getIdProduct(),orderItemsJpa.getActualPrice(),orderItemsJpa.getQuantity());
				orderItem.setProductId(orderItemsJpa.getIdProduct());
				orderItems.add(orderItem);
			}
			
			Order retrievedOrder = new Order(OrderStatus.values()[(int) (order.getIdOrderStatus() - 1)], order.getIdCustomer(), orderItems);
			retrievedOrder.setId(order.getIdOrder());
			retrievedOrder.setReadyToPrepare(order.getReadyToPrepare());
			orderSet.add(retrievedOrder);
		}
		return orderSet;
	}
}
