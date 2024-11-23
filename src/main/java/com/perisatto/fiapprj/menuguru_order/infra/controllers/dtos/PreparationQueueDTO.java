package com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos;

public class PreparationQueueDTO {
	private Long orderId;
	private String status;
	private String waitingTime;
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getWaitingTime() {
		return waitingTime;
	}
	
	public void setWaitingTime(String waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	
}
