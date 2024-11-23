package com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos;

import java.util.LinkedHashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;


public class GetOrderListResponseDTO {
	@JsonProperty(value = "_page")
	private Integer page;
	@JsonProperty(value = "_size")
	private Integer size;
	@JsonProperty(value = "_pageElements")
	private Integer pageElements;
	
	@JsonProperty(value = "_content")
	private Set<GetOrderResponseDTO> content = new LinkedHashSet<>();

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

	public Set<GetOrderResponseDTO> getContent() {
		return content;
	}

	public void setContent(Set<GetOrderResponseDTO> content) {
		this.content = content;
	}
	
	public void setContent(Set<Order> content, Integer page, Integer size) {
		this.setPage(page);
		this.setSize(size);
		this.setPageElements(content.size());
		ModelMapper customerMapper = new ModelMapper();
		
		for(Order order : content) {
			GetOrderResponseDTO newOrderResponse = customerMapper.map(order, GetOrderResponseDTO.class);
			this.content.add(newOrderResponse);
		}
	}
}
