package com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers;

import java.util.LinkedHashSet;
import java.util.Set;

import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderStatus;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.OrderEntity;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.OrderItemEntity;


public class OrderMapper {
	
	public Order mapToDomainEntity(OrderEntity OrderEntity) throws Exception {
		Set<OrderItem> items = new LinkedHashSet<OrderItem>();
		for(OrderItemEntity itemEntity : OrderEntity.getItems()) {
			OrderItem item = new OrderItem(itemEntity.getIdProduct(), itemEntity.getActualPrice(), itemEntity.getQuantity());
			item.setOrderItemId(itemEntity.getIdOrderItem());
			items.add(item);
		}
		Order order = new Order(OrderStatus.values()[(int) (OrderEntity.getIdOrderStatus() - 1)], OrderEntity.getIdCustomer(), items);		
		order.setId(OrderEntity.getIdOrder());
		order.setPaymentIdentifier(OrderEntity.getPaymentIdentifier());
		order.setReadyToPrepare(OrderEntity.getReadyToPrepare());
		return order;
	}
	
	public OrderEntity mapToJpaEntity(Order order) {		
		Set<OrderItemEntity> jpaItems = new LinkedHashSet<OrderItemEntity>();
		for(OrderItem item : order.getItems()) {
			OrderItemEntity itemEntity = new OrderItemEntity();
			itemEntity.setIdOrderItem(item.getOrderItemId());
			itemEntity.setIdProduct(item.getProductId());
			itemEntity.setActualPrice(item.getActualPrice());
			itemEntity.setQuantity(item.getQuantity());
			jpaItems.add(itemEntity);
		}
		
		OrderEntity OrderEntity = new OrderEntity();
		OrderEntity.setIdOrder(order.getId());
		OrderEntity.setIdCustomer(order.getCustomerId());
		OrderEntity.setIdOrderStatus(order.getStatus().getId());
		OrderEntity.setTotalPrice(order.getTotalPrice());
		OrderEntity.setItems(jpaItems);
		OrderEntity.setPaymentIdentifier(order.getPaymentIdentifier());
		OrderEntity.setReadyToPrepare(order.getReadyToPrepare());
		return OrderEntity;
		
	}
}
