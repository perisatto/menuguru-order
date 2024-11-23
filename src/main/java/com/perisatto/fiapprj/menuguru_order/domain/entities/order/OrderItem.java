package com.perisatto.fiapprj.menuguru_order.domain.entities.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru_order.handler.exceptions.ValidationException;

public class OrderItem {

	static final Logger logger = LogManager.getLogger(OrderItem.class);
	private String messageValidation;

	private Long orderItemId;
	private Long productId;
	private Double actualPrice;
	private Integer quantity;

	public OrderItem(Long productId, Double productActualPrice, Integer productQuantity) throws ValidationException {
		if(!validate(productId, productActualPrice, productQuantity)) {
			logger.warn(messageValidation);
			throw new ValidationException("ordr-2001", messageValidation);
		}

		this.productId = productId;
		this.actualPrice = productActualPrice;
		this.quantity = productQuantity;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	private boolean validate(Long productId, Double productPrice, Integer productQuantity) {
		messageValidation = "Error validating order data: ";		
		Boolean valid = true;

		if ((productId == null) || (productId <= 0)) {
			logger.debug("Error validating order data: null or equals zero product id");
			messageValidation = messageValidation + "null or equals zero product id |";
			valid = false;			
		}

		if (productPrice != null) {
			if (productPrice <= 0) {
				logger.debug("Error validating order data: null or invalid product actual price");
				messageValidation = messageValidation + "null or invalid product actual price |";
				valid = false;			
			}
		}

		if ((productQuantity == null) || (productQuantity == 0)) {
			logger.debug("Error validating order data: null or equals zero product quantity");
			messageValidation = messageValidation + "null or equals zero product quantity |";
			valid = false;			
		}

		return valid;
	}
}
