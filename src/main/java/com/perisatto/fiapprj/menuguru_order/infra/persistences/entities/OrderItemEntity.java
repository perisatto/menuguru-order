package com.perisatto.fiapprj.menuguru_order.infra.persistences.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OrderItem")
public class OrderItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOrderItem;
	private Long idProduct;
	private Double actualPrice;
	private Integer quantity;
	
	public Long getIdOrderItem() {
		return idOrderItem;
	}
	
	public void setIdOrderItem(Long idOrderItem) {
		this.idOrderItem = idOrderItem;
	}
	
	public Long getIdProduct() {
		return idProduct;
	}
	
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	
	public Double getActualPrice() {
		return actualPrice;
	}
	
	public void setActualPrice(Double actualPrice) {
		this.actualPrice = actualPrice;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}	
}
