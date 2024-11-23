package com.perisatto.fiapprj.menuguru_order.infra.gateways;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderStatus;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.OrderMapper;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.OrderEntity;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.OrderItemEntity;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories.OrderPersistenceRepository;

public class OrderRespositoryJpaTest {
	
	private OrderRepositoryJpa orderRepositoryJpa;
	
	@Mock
	private OrderPersistenceRepository orderPersistenceRepository;
	
	private OrderMapper orderMapper;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		orderMapper = new OrderMapper();
		orderRepositoryJpa = new OrderRepositoryJpa(orderPersistenceRepository, orderMapper);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}
	
	@Test
	void givenValidData_thenSavesOrder() throws Exception {
		
		OrderEntity orderEntity = getOrderEntity();
		
		when(orderPersistenceRepository.save(any()))
		.thenReturn(orderEntity);
		
		Order order = getOrder();
		
		orderRepositoryJpa.createOrder(order);
		
		verify(orderPersistenceRepository, times(1)).save(any());
	}
	
	@Test
	void givenValidId_thenRetrieveOrder() throws Exception {
		
		OrderEntity orderEntity = getOrderEntity();
		
		when(orderPersistenceRepository.findById(any(Long.class)))
		.thenReturn(Optional.of(orderEntity));
		
		orderRepositoryJpa.getOrder(10L);
		
		verify(orderPersistenceRepository, times(1)).findById(any(Long.class));
	}	
	
	@Test
	void givenInexistentId_thenRefusesRetrieveOrder() throws Exception {
		
		when(orderPersistenceRepository.findById(any(Long.class)))
		.thenReturn(Optional.empty());
		
		orderRepositoryJpa.getOrder(10L);
		
		verify(orderPersistenceRepository, times(1)).findById(any(Long.class));
	}
	
	@Test
	void listOrders() throws Exception {
		
		when(orderPersistenceRepository.findAll(any(Pageable.class)))
		.thenAnswer(i -> {
			List<OrderEntity> ordersList = new ArrayList<>();
			OrderEntity orderEntity1 = getOrderEntity();
			OrderEntity orderEntity2 = getOrderEntity();
			ordersList.add(orderEntity1);
			ordersList.add(orderEntity2);
			
			Page<OrderEntity> orders = new PageImpl<>(ordersList);
			
			return orders;
		});
		
		orderRepositoryJpa.findAll(50, 1);
		
		verify(orderPersistenceRepository, times(1)).findAll(any(Pageable.class));
	}
	
	@Test
	void givenValidStatus_thenUpdatesOrder() throws Exception {
		OrderEntity orderEntity = getOrderEntity();
		
		when(orderPersistenceRepository.save(any(OrderEntity.class)))
		.thenReturn(orderEntity);
		
		Order order = getOrder();
		
		orderRepositoryJpa.updateOrder(order);
		
		verify(orderPersistenceRepository, times(1)).save(any(OrderEntity.class));		
	}
	
	@Test
	void listPreparationQueue() throws Exception {
		
		when(orderPersistenceRepository.findByIdOrderStatusBetween(any(Long.class), any(Long.class), any(Pageable.class)))
		.thenAnswer(i -> {
			List<OrderEntity> ordersList = new ArrayList<>();
			OrderEntity orderEntity1 = getOrderEntity();
			OrderEntity orderEntity2 = getOrderEntity();
			ordersList.add(orderEntity1);
			ordersList.add(orderEntity2);
			
			Page<OrderEntity> orders = new PageImpl<>(ordersList);
			
			return orders;
		});
		
		orderRepositoryJpa.listPreparationQueue(50, 1);
		
		verify(orderPersistenceRepository, times(1)).findByIdOrderStatusBetween(any(Long.class), any(Long.class), any(Pageable.class));
	}
	
	private OrderEntity getOrderEntity() throws Exception {
		Set<OrderItemEntity> orderItems = new LinkedHashSet<OrderItemEntity>();

		OrderItemEntity orderItem = new OrderItemEntity();
		orderItem.setIdOrderItem(10L);
		orderItem.setIdProduct(10L);
		orderItem.setActualPrice(10.0);
		orderItem.setQuantity(1);

		orderItems.add(orderItem);

		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setIdCustomer(10L);
		orderEntity.setIdOrder(10L);
		orderEntity.setIdOrderStatus(OrderStatus.PENDENTE_PAGAMENTO.getId());
		orderEntity.setItems(orderItems);
		orderEntity.setPaymentIdentifier("000000");
		orderEntity.setReadyToPrepare(new Date());
		orderEntity.setTotalPrice(10.0);
		
		return orderEntity;
	}
	
	public static Order getOrder() throws Exception {
		Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();

		OrderItem orderItem = new OrderItem(10L, 10.0, 1);

		orderItems.add(orderItem);

		Order order = new Order(OrderStatus.EM_PREPARACAO, 10L, orderItems);
		order.setReadyToPrepare(new Date());
		order.setWaitingTime(Duration.ZERO);
		return order;
	}
}
