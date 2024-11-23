package com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;


public class GetOrderPraparationQueueResponseDTO {
	@JsonProperty(value = "_page")
	private Integer page;
	@JsonProperty(value = "_size")
	private Integer size;
	@JsonProperty(value = "_pageElements")
	private Integer pageElements;
	
	@JsonProperty(value = "_content")
	private Set<PreparationQueueDTO> content = new LinkedHashSet<>();

	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
		
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPageElements() {
		return pageElements;
	}

	public void setPageElements(Integer pageElements) {
		this.pageElements = pageElements;
	}

	public Set<PreparationQueueDTO> getContent() {
		return content;
	}

	public void setContent(Set<PreparationQueueDTO> content) {
		this.content = content;
	}
	
	public void setContent(Set<Order> content, Integer page, Integer size) {
		this.setPage(page);
		this.setSize(size);
		this.setPageElements(content.size());
	
		for(Order order : content) {
			PreparationQueueDTO queueItem = new PreparationQueueDTO();
			queueItem.setOrderId(order.getId());
			queueItem.setStatus(order.getStatus().toString());
			queueItem.setWaitingTime(order.getWaitingTime().toHoursPart() + "h " + order.getWaitingTime().toMinutesPart() + "m" );
			this.content.add(queueItem);
		}
	}
}
