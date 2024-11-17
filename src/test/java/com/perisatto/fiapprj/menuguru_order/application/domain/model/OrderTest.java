package com.perisatto.fiapprj.menuguru_order.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderStatus;
import com.perisatto.fiapprj.menuguru_order.handler.exceptions.ValidationException;

@ActiveProfiles(value = "test")
public class OrderTest {

	@Test
	void givenValidParameters_thenCreateOrder() throws Exception {
		OrderStatus orderStatus = OrderStatus.RECEBIDO;
		Long customerId = 1L;

		Long firstProductId = 1L;
		Double firstProductActualPrice = 35.50;
		Integer firstProductQuantity = 1;		
		OrderItem firstItem = new OrderItem(firstProductId, firstProductActualPrice, firstProductQuantity);

		Long secondProductId = 1L;
		Double secondProductActualPrice = 35.50;
		Integer secondProductQuantity = 1;		
		OrderItem secondItem = new OrderItem(secondProductId, secondProductActualPrice, secondProductQuantity);		

		Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
		orderItems.add(firstItem);
		orderItems.add(secondItem);

		Order order = new Order(orderStatus, customerId, orderItems);

		assertThat(order.getCustomerId()).isEqualTo(customerId);
		assertThat(order.getStatus()).isEqualTo(orderStatus);
		assertThat(order.getItems().size()).isEqualTo(2);
	}

	@Test
	void givenInvalidCustomerId_thenRefuseCreateOrder() {
		try { 
			OrderStatus orderStatus = OrderStatus.RECEBIDO;
			Long customerId = 0L;

			Long firstProductId = 1L;
			Double firstProductActualPrice = 35.50;
			Integer firstProductQuantity = 1;		
			OrderItem firstItem = new OrderItem(firstProductId, firstProductActualPrice, firstProductQuantity);

			Long secondProductId = 1L;
			Double secondProductActualPrice = 35.50;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, secondProductActualPrice, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);

			Order order = new Order(orderStatus, customerId, orderItems);
			
			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}
	
	@Test
	void givenInvalidProductId_thenRefuseCreateOrder() {
		try { 
			OrderStatus orderStatus = OrderStatus.RECEBIDO;
			Long customerId = 1L;

			Long firstProductId = 0L;
			Double firstProductActualPrice = 35.50;
			Integer firstProductQuantity = 1;		
			OrderItem firstItem = new OrderItem(firstProductId, firstProductActualPrice, firstProductQuantity);

			Long secondProductId = 1L;
			Double secondProductActualPrice = 35.50;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, secondProductActualPrice, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);

			Order order = new Order(orderStatus, customerId, orderItems);

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}	
	
	
	@Test
	void givenInvalidProductPrice_thenRefuseCreateOrder() {
		try { 
			OrderStatus orderStatus = OrderStatus.RECEBIDO;
			Long customerId = 1L;

			Long firstProductId = 1L;
			Double firstProductActualPrice = -34.89;
			Integer firstProductQuantity = 1;		
			OrderItem firstItem = new OrderItem(firstProductId, firstProductActualPrice, firstProductQuantity);

			Long secondProductId = 1L;
			Double secondProductActualPrice = 35.50;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, secondProductActualPrice, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);

			Order order = new Order(orderStatus, customerId, orderItems);

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}
	
	@Test
	void givenInvalidProductQuantity_thenRefuseCreateOrder() {
		try { 
			OrderStatus orderStatus = OrderStatus.RECEBIDO;
			Long customerId = 1L;

			Long firstProductId = 1L;
			Double firstProductActualPrice = 34.89;
			Integer firstProductQuantity = 0;		
			OrderItem firstItem = new OrderItem(firstProductId, firstProductActualPrice, firstProductQuantity);

			Long secondProductId = 1L;
			Double secondProductActualPrice = 35.50;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, secondProductActualPrice, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);

			Order order = new Order(orderStatus, customerId, orderItems);

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}
	
	@Test
	void givenInvalidOrderStatus_thenRefuseCreateOrder() {
		try { 
			OrderStatus orderStatus = null;
			Long customerId = 1L;

			Long firstProductId = 1L;
			Double firstProductActualPrice = 34.89;
			Integer firstProductQuantity = 0;		
			OrderItem firstItem = new OrderItem(firstProductId, firstProductActualPrice, firstProductQuantity);

			Long secondProductId = 1L;
			Double secondProductActualPrice = 35.50;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, secondProductActualPrice, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);

			Order order = new Order(orderStatus, customerId, orderItems);

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}
}

